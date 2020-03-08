package com.mrfox.prize.message;

import com.google.common.base.Throwables;
import com.mongodb.client.result.UpdateResult;
import com.mrfox.prize.bean2bean.MappingInstance;
import com.mrfox.prize.contants.PrizeDrawRecordRepeatStatus;
import com.mrfox.prize.dto.PrizeDrawRecordDto;
import com.mrfox.prize.mapper.*;
import com.mrfox.prize.model.PrizeDrawRecordRepeat;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/*****
 * 消费者
 * @author     : MrFox
 * @date       : 2020-02-02 11:50
 * @description:
 * @version    :
 ****/
@Slf4j
@Service
@RocketMQMessageListener(topic = "prize", consumerGroup = "prize-group")
public class PrizeConsumer implements RocketMQListener<PrizeDrawRecordDto> {

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private UserMapper userMapper;

    @Resource
    private AwardPrizeMapper awardPrizeMapper;

    @Resource
    private AwardMapper awardMapper;

    @Resource
    private ActiveAwardMapper activeAwardMapper;

    @Resource
    private UserPointRecordMapper userPointRecordMapper;

    @Resource
    private ActiveDrawRecordMapper activeDrawRecordMapper;

    @Recover
    public void recover(Exception e){
        log.warn("减库存失败！！！:{}",Throwables.getStackTraceAsString(e));
    }

    @Transactional(rollbackFor = Exception.class)
    @Retryable(value = Exception.class,maxAttempts = 3,backoff = @Backoff(delay = 2000,multiplier = 1.5))
    public void onMessage(PrizeDrawRecordDto prizeDrawRecordDto) {
        log.info("received message: " + prizeDrawRecordDto);
        String orderId = prizeDrawRecordDto.getOrderId();

        try {
            //幂等性校验
            if (!isRepeat(orderId)) {
                //更新mongo状态--spring事务管理mongo仅支持4.0以上的mongo并且是副本集,不支持单server
                UpdateResult updateResult = mongoTemplate.upsert(Query.query(Criteria.where("orderId").is(orderId).and("status").is(PrizeDrawRecordRepeatStatus.HANDLE)),
                        new Update().set("orderId", orderId).set("status", PrizeDrawRecordRepeatStatus.HANDLE.getStatus())
                                .set("handleTime", LocalDateTime.now()), PrizeDrawRecordRepeat.class);

                if(Objects.nonNull(updateResult.getUpsertedId())){
                    //更新数据库用户积分及积分消费记录
                    userMapper.updatePointById(prizeDrawRecordDto.getUserId(),prizeDrawRecordDto.getUsePoint());
                    userPointRecordMapper.insertRecord(MappingInstance.BASE_MAPPING.convertToUserPointRecord(prizeDrawRecordDto));
//                    //判断是否获奖--更新奖品/奖项库存并生成奖品记录
//                    if(prizeDrawRecordDto.getIsWin()){
//                        Integer upAwardPrize = awardPrizeMapper.updatePrizeRemainNum(prizeDrawRecordDto.getAwardId());
//                        if(upAwardPrize<1){
//                            throw new ServiceException(500,"奖项对应奖品表更新异常,存在超卖");
//                        }
//
//                        Integer updateIssueNum = awardMapper.updateIssueNum(prizeDrawRecordDto.getAwardId());
//                        if(updateIssueNum<1){
//                            throw new ServiceException(500,"奖项表更新异常,存在超卖");
//                        }
//
//                        Integer updateAwardRealNum = activeAwardMapper.updateAwardRealNum(prizeDrawRecordDto.getAwardId(), prizeDrawRecordDto.getActiveId());
//                        if(updateAwardRealNum<1){
//                            throw new ServiceException(500,"活动表对应奖项表更新异常,存在超卖");
//                        }
//
//                        activeDrawRecordMapper.insertSelective(MappingInstance.BASE_MAPPING.convertToActiveDrawRecord(prizeDrawRecordDto));
//
//
//                    }
                }
            }
        } catch (Exception e) {
            String stackTraceAsString = Throwables.getStackTraceAsString(e);
            log.error("[PrizeConsumer.onMessage] is error:{},prizeDrawRecordDto:{}", stackTraceAsString,prizeDrawRecordDto);
            //这里需要定时任务扫描失败,重试逻辑由定时任务保证
            mongoTemplate.updateFirst(Query.query(Criteria.where("orderId").is(orderId).and("status").is(PrizeDrawRecordRepeatStatus.HANDLE)),
                    new Update().set("orderId", orderId)
                            .set("status", PrizeDrawRecordRepeatStatus.HANDLE_ERROR.getStatus())
                            .set("handleTime", LocalDateTime.now())
                            .set("prizeDrawRecordDto",prizeDrawRecordDto)
                            .set("errorStr",stackTraceAsString), PrizeDrawRecordRepeat.class);
            //设置手动回滚
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
        }

    }


    /*****
     * 幂等性校验
     * @param
     * @return
     * @description:
     ****/
    private Boolean isRepeat(String orderId) {
        return mongoTemplate.exists(Query.query(Criteria.where("orderId").is(orderId).and("status").is(PrizeDrawRecordRepeatStatus.HANDLE)), PrizeDrawRecordRepeat.class);
    }
}
