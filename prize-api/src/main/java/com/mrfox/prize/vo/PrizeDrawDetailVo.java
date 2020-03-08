package com.mrfox.prize.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrizeDrawDetailVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     * */
    private Long id;


    /**
     * 活动限制次数
     */
    private Integer limitNum;

    /**
     * 活动限制时间单位(0-小时,1-天,2-月)
     */
    private Integer limitDateType;

    /**
     * 活动限制时间
     */
    private Integer limitDateNum;

    /**
     * 活动抽奖所需积分数(单次)
     */
    private Integer needPointNum;

    /**
     * 活动有效期-开始
     */
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime effectiveTimeStart;

    /**
     * 活动有效期-结束
     */
    private LocalDateTime effectiveTimeEnd;

    /**
     * 是否存在
     */
    private Integer isExists;

    /**
     * 抽奖奖项集合
     * */
    private List<ActivePrizeDetailVo> activePrizeDetailVoList;
}
