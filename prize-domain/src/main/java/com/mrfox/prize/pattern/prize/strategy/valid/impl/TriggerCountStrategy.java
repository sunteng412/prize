package com.mrfox.prize.pattern.prize.strategy.valid.impl;

import com.mrfox.prize.constants.PrizeErrorCode;
import com.mrfox.prize.dto.PrizeDrawDto;
import com.mrfox.prize.model.UserDrawRecord;
import com.mrfox.prize.pattern.prize.strategy.valid.PrizeDrawRequest;
import com.mrfox.prize.pattern.prize.strategy.valid.Strategy;
import com.mrfox.prize.pattern.prize.strategy.valid.ValidStrategy;
import com.mrfox.prize.resonse.Response;
import com.mrfox.prize.utils.LocalDateTimeUtils;
import com.mrfox.prize.vo.PrizeDrawDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/*****
 * 判断触发次数
 * @author     : MrFox
 * @date       : 2020-01-27 15:53
 * @description:
 * @version    :
 ****/
@Strategy(modelName = TriggerCountStrategy.NAME)
@Component
@Slf4j
public class TriggerCountStrategy implements ValidStrategy {

    static final String NAME = "triggerCount";

    @Resource
    private MongoTemplate mongoTemplate;


    /*****
     * 判断是否经过校验
     * @param
     * @return
     * @description:
     ****/
    @Override
    public Response valid(PrizeDrawRequest prizeDrawRequest) {
        UserDrawRecord userDrawRecord = prizeDrawRequest.getUserDrawRecord();
        PrizeDrawDetailVo prizeDrawDetailVo = prizeDrawRequest.getPrizeDrawDetailVo();
        log.info("[TriggerCountStrategy.valid] prizeDrawRequest:{}",prizeDrawRequest);
        if(Objects.isNull(userDrawRecord.getDrawCount())){
            userDrawRecord.setDrawCount(1);
            userDrawRecord.setLastDrawTime(LocalDateTime.now());
            mongoTemplate.insert(userDrawRecord);
            return null;
        }else {
            //不在同一天
            if(LocalDateTimeUtils.betweenTwoTimeByDay(LocalDateTime.now(),userDrawRecord.getLastDrawTime())>=1){
                mongoTemplate.upsert(Query.query(Criteria.where("userId").is(userDrawRecord.getUserId()).and("activeId").is(userDrawRecord.getActiveId())), new Update().set("drawCount",1).set("lastDrawTime",LocalDateTime.now()),UserDrawRecord.class);
            }else {
                if(userDrawRecord.getDrawCount()>=prizeDrawDetailVo.getLimitDateNum()){
                    log.info("[TriggerCountStrategy.valid]抽奖次数超过限制,次数:{},限制次数:{}",userDrawRecord.getDrawCount(),prizeDrawDetailVo.getLimitDateNum());
                    return Response.no(400, PrizeErrorCode.DRAW_COUNT_IS_OVER_THE_LIMIT.code(), PrizeErrorCode.DRAW_COUNT_IS_OVER_THE_LIMIT.errMsg());
                }else {
                    mongoTemplate.upsert(Query.query(Criteria.where("userId").is(userDrawRecord.getUserId()).and("activeId").is(userDrawRecord.getActiveId())), new Update().inc("drawCount",1).set("lastDrawTime",LocalDateTime.now()),UserDrawRecord.class);
                    return null;
                }
            }

        }
        return null;
    }


    /*****
     * 构建查询对象
     * @param prizeDrawDto 抽奖请求
     * @param prizeDrawRequest 抽奖请求信息封装
     * @return
     * @description:
     ****/
    @Override
    public void buildValid(PrizeDrawDto prizeDrawDto, PrizeDrawRequest prizeDrawRequest) {
        //查询用户抽奖记录
        UserDrawRecord userDrawRecord = mongoTemplate.findOne(
                Query.query(Criteria.where("userId").is(prizeDrawDto.getUserId())
                        .and("activeId").is(prizeDrawDto.getActiveId())), UserDrawRecord.class);
        if(Objects.isNull(userDrawRecord)){
            userDrawRecord = new UserDrawRecord();
            userDrawRecord.setUserId(prizeDrawDto.getUserId());
            userDrawRecord.setActiveId(prizeDrawDto.getActiveId());
        }
        prizeDrawRequest.setUserDrawRecord(userDrawRecord);
        prizeDrawRequest.setError(valid(prizeDrawRequest));
    }
}
