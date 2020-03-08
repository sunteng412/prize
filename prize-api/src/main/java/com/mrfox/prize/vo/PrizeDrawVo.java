package com.mrfox.prize.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrizeDrawVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 中奖状态 1-中奖,2-未中奖,3-未知
     */
    private Integer status;

    /**
     * 中奖者ID
     */
    private Long userId;

    /**
     * 中奖活动id
     * */
    private Long activeId;

    /**
     * 中奖奖项id
     * */
    private Long awardId;
}
