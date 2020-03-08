package com.mrfox.prize.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

@Data
public class Award implements Serializable {
    private Long id;

    /**
     * 奖项名称
     */
    private String name;

    /**
     * 奖项描述
     */
    private String describe;

    /**
     * 奖项图片(默认为第一个奖品的图片)
     */
    private String img;

    /**
     * 奖品总数量
     */
    private Integer totalNum;

    /**
     * 已发放数量
     */
    private Integer issueNum;

    /**
     * 可用数量
     */
    private Integer useableNum;

    /**
     * 奖品详细说明
     */
    private String detailDescribe;

    /**
     * 奖项发放有效期-开始
     */
    private LocalDateTime effectiveTimeStart;

    /**
     * 奖项发放有效期-结束
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
    private Date updateAt;

    /**
     * 修改人
     */
    private Long updateBy;

    private static final long serialVersionUID = 1L;
}