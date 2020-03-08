package com.mrfox.prize.impl;

import com.mrfox.prize.api.PrizeDrawApi;
import com.mrfox.prize.bean2bean.MappingInstance;
import com.mrfox.prize.contants.PrizeLuckyStatus;
import com.mrfox.prize.dto.PrizeDrawDto;
import com.mrfox.prize.dto.PrizeDrawRecordDto;
import com.mrfox.prize.manager.PrizeDrawManager;
import com.mrfox.prize.pattern.prize.strategy.select.SelectHandle;
import com.mrfox.prize.pattern.prize.strategy.select.SelectResult;
import com.mrfox.prize.pattern.prize.strategy.valid.PrizeDrawRequest;
import com.mrfox.prize.pattern.prize.strategy.valid.ValidStrategyFactory;
import com.mrfox.prize.resonse.Response;
import com.mrfox.prize.utils.SnowFlake;
import com.mrfox.prize.vo.PrizeDrawVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.Objects;

/*****
 * 用户抽奖业务控制层
 * @author     : MrFox
 * @date       : 2020-01-24 22:42
 * @description:
 * @version    :
 ****/
@Service
@Slf4j
public class PrizeDrawImpl implements PrizeDrawApi {

    @Resource
    private SelectHandle selectHandle;

    @Resource
    private ValidStrategyFactory validStrategyFactory;

    @Resource
    private PrizeDrawManager prizeDrawManager;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /*****
     * 抽奖
     * @param
     * @return
     * @description:
     ****/
    @Override
    public Response<PrizeDrawVo> prizeDraw(PrizeDrawDto prizeDrawDto) {
        log.info("[PrizeDrawImpl.prizeDraw]prizeDrawDto:{}", prizeDrawDto);

        //传入分析器,分析 用户抽奖次数，用户积分额度，抽奖奖品信息 是否上限及判断奖品库存
        PrizeDrawRequest prizeDrawRequest = validStrategyFactory.buildValid(prizeDrawDto);
        Response error = prizeDrawRequest.getError();
        if (Objects.nonNull(error)) {
            return error;
        }
       //构建抽奖算法
        SelectResult select = selectHandle.select(null, prizeDrawRequest.getPrizeDrawDetailVo().getActivePrizeDetailVoList());
        log.info("[PrizeDrawImpl.prizeDraw]prizeDrawDto:{},select:{}", prizeDrawDto, select);
        PrizeDrawVo prizeDrawVo = new PrizeDrawVo();
        prizeDrawVo.setUserId(prizeDrawDto.getUserId());
        prizeDrawVo.setActiveId(prizeDrawDto.getActiveId());
        if (select.getIsWin() && prizeDrawManager.reduceAwardNum(prizeDrawDto.getActiveId(), select.getAwardId())) {
            //扣减奖品库存 扣除库存时需要重新读取并加锁,因为可能出现超卖的情况
            prizeDrawVo.setStatus(PrizeLuckyStatus.WIN.getStatus());
        } else {
            prizeDrawVo.setStatus(PrizeLuckyStatus.FAIL.getStatus());
        }

        //构建最后生成的
        PrizeDrawRecordDto prizeDrawRecordDto = MappingInstance.BASE_MAPPING.convertToPrizeDrawRecordDto(prizeDrawRequest, select);
        prizeDrawRecordDto.setOrderId(snowFlake.nextId()+"");

        SendResult sendResult = rocketMQTemplate.syncSend("prize", MessageBuilder.withPayload(prizeDrawRecordDto).build());

        return Response.yes(prizeDrawVo);
    }
}
