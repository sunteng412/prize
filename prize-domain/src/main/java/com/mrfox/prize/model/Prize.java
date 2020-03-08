package com.mrfox.prize.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

@Data
public class Prize implements Serializable {
    private Long id;

    /**
     * 奖品名称
     */
    private String name;

    /**
     * 奖品描述
     */
    private String describe;

    /**
     * 奖品类型(0-乘车券,1-三方商品,2-碳币,3-实物)
     */
    private Integer category;

    /**
     * 奖品跳转页面
     */
    private Integer jumpUrl;

    /**
     * 可用奖品量(奖品总数量-锁定奖品量)
     */
    private Integer useableNum;

    /**
     * 奖品总数量
     */
    private Integer totalNum;

    /**
     * 锁定奖品量
     */
    private Integer lockNum;

    /**
     * 库存量
     */
    private Integer realNum;

    /**
     * 奖品图片
     */
    private String img;

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
    private Date updateAt;

    /**
     * 修改人
     */
    private Long updateBy;

    private static final long serialVersionUID = 1L;
}