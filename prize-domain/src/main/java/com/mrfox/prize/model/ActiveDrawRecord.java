package com.mrfox.prize.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class ActiveDrawRecord implements Serializable {
    private Integer id;

    /**
     * 流水id
     */
    private String orderId;

    /**
     * 抽奖时间
     */
    private Date drawTime;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 抽奖积分数
     */
    private String usePoint;

    /**
     * 活动id
     */
    private Integer activeId;

    /**
     * 奖项id
     */
    private Integer awardId;

    private static final long serialVersionUID = 1L;
}