package com.mrfox.prize.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ActiveAward implements Serializable {
    private Long id;

    /**
     * 抽奖活动id
     */
    private Long activeId;

    /**
     * 奖项id
     */
    private Long awardId;

    /**
     * 抽奖可用数量
     */
    private Integer awardNum;

    /**
     * 抽奖已用数量
     */
    private String awardRealNum;

    /**
     * 概率(百分比)
     */
    private BigDecimal probability;

    private static final long serialVersionUID = 1L;
}