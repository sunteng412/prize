package com.mrfox.prize.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class AwardPrize implements Serializable {
    private Long id;

    /**
     * 奖项id
     */
    private Long awardId;

    /**
     * 奖品id
     */
    private Integer prizeId;

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