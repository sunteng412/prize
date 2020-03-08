package com.mrfox.prize.pattern.prize.strategy.valid;

import com.mrfox.prize.dto.PrizeDrawDto;
import com.mrfox.prize.resonse.Response;

/*****
 * 设置策略
 * @author     : MrFox
 * @date       : 2020-01-27 15:45
 * @description:
 * @version    :
 ****/
public interface ValidStrategy {

    /*****
     * 判断触发
     * @param prizeDrawRequest 抽奖请求实体
     * @return {@link Response}
     * @description:
     ****/
    Response valid(PrizeDrawRequest prizeDrawRequest);

    /*****
     * 构建查询对象
     * @param prizeDrawDto 抽奖
     * @return
     * @description:
     ****/
    void buildValid(PrizeDrawDto prizeDrawDto,PrizeDrawRequest prizeDrawRequest);


}
