package com.mrfox.prize.model;

import com.mrfox.prize.dto.PrizeDrawRecordDto;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("prize_draw_record_repeat")
@Data
public class PrizeDrawRecordRepeat {

    @Id
    private ObjectId id;

    /**
     * 订单流水ID
     * */
    private String orderId;

    /**
     * 当前状态
     * */
    private Integer status;

    /**
     *
     * 处理时间
     * */
    private LocalDateTime handleTime;

    /**
     * 失败数据
     * */
    private PrizeDrawRecordDto prizeDrawRecordDto;

    /**
     * 失败原因
     * */
    private String errorStr;

    /**
     * 重试次数
     * */
    private Integer retryCount;

    /**
     * 重试失败原因
     * */
    private String retryErrorStr;
}
