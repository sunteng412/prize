package com.mrfox.prize.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ActivePrizeDetailVo implements Serializable {

    /**
     * 奖项id
     */
    private Long activeId;

    /**
    * 奖项id
    */
    private Long awardId;

    /**
     * 奖项数量
     */
    private Integer awardNum;

    /**
     * 奖项实际数量
     */
    private Integer awardRealNum;


    /**
     * 概率(百分比)
     */
    private BigDecimal probability;

    /**
    * 奖品id
    */
    private Long prizeId;

    /**
    * 奖品发放数量(对应该奖项)
    */
    private Integer prizeNum;

    /**
    * 奖品剩余数量(对应该奖项)
    */
    private Integer prizeRemainNum;

    private static final long serialVersionUID = 1L;
}