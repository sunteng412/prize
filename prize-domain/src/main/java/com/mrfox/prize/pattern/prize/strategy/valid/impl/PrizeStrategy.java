package com.mrfox.prize.pattern.prize.strategy.valid.impl;

import com.mrfox.prize.constants.PrizeErrorCode;
import com.mrfox.prize.dto.PrizeDrawDto;
import com.mrfox.prize.manager.PrizeDrawManager;
import com.mrfox.prize.pattern.prize.strategy.valid.PrizeDrawRequest;
import com.mrfox.prize.pattern.prize.strategy.valid.Strategy;
import com.mrfox.prize.pattern.prize.strategy.valid.ValidStrategy;
import com.mrfox.prize.resonse.Response;
import com.mrfox.prize.vo.PrizeDrawDetailVo;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/*****
 * 判断活动信息
 * @author     : MrFox
 * @date       : 2020-01-27 15:53
 * @description:
 * @version    :
 ****/
@Strategy(modelName = PrizeStrategy.NAME,sort = 1)
@Component
public class PrizeStrategy implements ValidStrategy {

    static final String NAME = "prize";


    @Resource
    private PrizeDrawManager prizeDrawManager;

    /*****
     * 判断活动是否在有效期内
     * @param prizeDrawRequest
     * @return
     * @description:
     ****/
    @Override
    public Response valid(PrizeDrawRequest prizeDrawRequest) {
        PrizeDrawDetailVo prizeDrawDetailVo  = prizeDrawRequest.getPrizeDrawDetailVo();
        if(Objects.isNull(prizeDrawDetailVo)){
            return Response.no(400, PrizeErrorCode.ACTIVE_IS_EXPIRE.code(), PrizeErrorCode.ACTIVE_IS_EXPIRE.errMsg());
        }

        //判断是否过期
        if (LocalDateTime.now().isAfter(prizeDrawDetailVo.getEffectiveTimeStart())
                && LocalDateTime.now().isBefore(prizeDrawDetailVo.getEffectiveTimeEnd())) {
            return null;
        } else {
            return Response.no(400, PrizeErrorCode.ACTIVE_IS_EXPIRE.code(), PrizeErrorCode.ACTIVE_IS_EXPIRE.errMsg());
        }

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
        //查询抽奖页信息
        PrizeDrawDetailVo prizeDrawDetailVo = prizeDrawManager.selectActiveDetail(prizeDrawDto);
        prizeDrawRequest.setPrizeDrawDetailVo(prizeDrawDetailVo);
        prizeDrawRequest.setError(valid(prizeDrawRequest));
    }
}
