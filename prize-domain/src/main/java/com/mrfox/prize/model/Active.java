package com.mrfox.prize.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Active implements Serializable {
    private Long id;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 限制次数
     */
    private Integer limitNum;

    /**
     * 限制时间单位(0-小时,1-天,2-月)
     */
    private Integer limitDateType;

    /**
     * 限制时间
     */
    private Integer limitDateNum;

    /**
     * 抽奖所需积分数(单次)
     */
    private Integer needPointNum;

    /**
     * 奖品发放有效期-开始
     */
    private LocalDateTime effectiveTimeStart;

    /**
     * 奖品发放有效期-结束
     */
    private LocalDateTime effectiveTimeEnd;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    private LocalDateTime updateAt;

    /**
     * 修改人
     */
    private Long updateBy;

    private static final long serialVersionUID = 1L;
}