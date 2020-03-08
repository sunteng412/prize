package com.mrfox.prize.pattern.prize.strategy.select;

import com.mrfox.prize.pattern.prize.strategy.select.impl.RandomDrawSelect;
import com.mrfox.prize.vo.ActivePrizeDetailVo;

import java.util.List;

/*****
 * 抽奖算法
 * @author     : MrFox
 * @date       : 2020-01-28 22:12
 * @description: {@link SPI 为默认实现}
 * @version    :
 ****/
@SPI(RandomDrawSelect.NAME)
public interface DrawSelect {

    /*****
     * 抽奖信息
     * @param
     * @return
     * @description:
     ****/
    SelectResult select(List<ActivePrizeDetailVo> activePrizeDetailVoList);
}
