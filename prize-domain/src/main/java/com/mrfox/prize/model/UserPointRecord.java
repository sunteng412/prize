package com.mrfox.prize.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserPointRecord implements Serializable {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 业务流水id
     */
    private String businessNo;

    /**
     * 业务类型(1-积分抽奖)
     */
    private Integer businessType;

    /**
     * 使用积分数
     */
    private Integer usePoint;

    /**
     * 发生时间
     */
    private LocalDateTime handleTime;

    /**
     * 类型(1-获取,2-消费)
     */
    private Integer type;

    /**
     * 是否已删除(0-否，1为是)
     */
    private Byte deleteFlag;

    private static final long serialVersionUID = 1L;
}