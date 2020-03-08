package com.mrfox.prize.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/*****
 * 用户抽奖次数
 * @author     : MrFox
 * @date       : 2020-01-27 15:02
 * @description:
 * @version    :
 ****/
@Document("user_draw")
@Data
public class UserDrawRecord {

    @Id
    private ObjectId id;

    /**
     * 用户id
     * */
    private Long userId;

    /**
     * 活动id
     * */
    private Long activeId;

    /**
     * 最后抽奖时间
     * */
    private LocalDateTime lastDrawTime;

    /**
     * 抽奖次数
     * */
    private Integer drawCount;

}
