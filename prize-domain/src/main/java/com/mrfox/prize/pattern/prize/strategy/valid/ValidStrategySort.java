package com.mrfox.prize.pattern.prize.strategy.valid;

import lombok.AllArgsConstructor;
import lombok.Data;

/*****
 * 策略排序实体
 * @author     : MrFox
 * @date       : 2020-01-28 15:48
 * @description:
 * @version    :
 ****/
@Data
@AllArgsConstructor
public class ValidStrategySort implements Comparable<ValidStrategySort>{
    /**
     * 策略排序
     * */
    Integer sort;

    /**
     * 策略名
     * */
    String validStrategyName;

    @Override
    public int compareTo(ValidStrategySort validStrategySort) {
        return this.sort;
    }
}
