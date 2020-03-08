package com.mrfox.prize.pattern.prize.strategy.select.impl;

import com.mrfox.prize.pattern.prize.strategy.select.DrawSelect;
import com.mrfox.prize.pattern.prize.strategy.select.SelectResult;
import com.mrfox.prize.vo.ActivePrizeDetailVo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public abstract class AbstractDrawSelect implements DrawSelect {

    private final Random random = new Random();

    private final BigDecimal bigDecimal = new BigDecimal(100);

    /*****
     * 抽奖策略
     * @param
     * @return
     * @description:
     ****/
    public SelectResult select(List<ActivePrizeDetailVo> activePrizeDetailVoList){
        if(activePrizeDetailVoList.isEmpty()){
            //如果没有查到奖项信息
            return new SelectResult(Boolean.FALSE,null);
        }else if(activePrizeDetailVoList.size()==1){
            //如果只有一个
            ActivePrizeDetailVo activePrizeDetailVo = activePrizeDetailVoList.get(0);
            //比如抽奖概率是0.10(最多两位小数)  0.1% = 10/10000
            if(random.nextInt(10000) > activePrizeDetailVo.getProbability().multiply(bigDecimal).intValue()){
                return new SelectResult(Boolean.FALSE,null);
            }else {
                return new SelectResult(Boolean.TRUE,activePrizeDetailVo.getAwardId());
            }
        }else {
            //执行抽奖策略
            return doSelect(activePrizeDetailVoList);
        }
    }

    /*****
     * 实际执行抽奖策略
     * @param
     * @return
     * @description:
     ****/
    protected abstract SelectResult doSelect(List<ActivePrizeDetailVo> activePrizeDetailVoList);
}
