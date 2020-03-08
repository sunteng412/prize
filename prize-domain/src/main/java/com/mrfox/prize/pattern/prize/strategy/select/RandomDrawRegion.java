package com.mrfox.prize.pattern.prize.strategy.select;

import lombok.Data;

/*****
 * 随机概率实体
 * @author     : MrFox
 * @date       : 2020-01-29 21:30
 * @description:
 * @version    :
 ****/
@Data
public class RandomDrawRegion {

    /**
     * 概率-换算万分之几
     */
    private Integer probability;


    /**
     * 概率开始区间
     */
    private Integer probabilityStart;

    /**
     * 概率开始区间
     */
    private Integer probabilityEnd;

    /**
     * 奖项id
     * */
    private Long awardId;
}
