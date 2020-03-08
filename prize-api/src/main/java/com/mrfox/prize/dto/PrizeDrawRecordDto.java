package com.mrfox.prize.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/*****
 * 抽奖流水
 * @author     : MrFox
 * @date       : 2020-01-31 22:30
 * @description:
 * @version    :
 ****/
@Data
public class PrizeDrawRecordDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单流水ID
     * */
    private String orderId;

    /**
     * 最后抽奖时间
     * */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime drawTime;

    /**
     * 用户ID
     * */
    private Long userId;

    /**
     * 抽奖积分数
     * */
    private Integer usePoint;

    /**
     * 活动id
     * */
    private Long activeId;

    /**
     * 是否中奖
     * */
    private Boolean isWin;

    /**
     * 奖项Id
     * */
    private Long awardId;


}
