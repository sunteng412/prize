package com.mrfox.prize.dto;

import lombok.Data;

import java.io.Serializable;

/*****
 * 抽奖dto
 * @author     : MrFox
 * @date       : 2020-01-24 22:13
 * @description:
 * @version    :
 ****/
@Data
public class PrizeDrawDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 活动id
     */
    private Long activeId;
}
