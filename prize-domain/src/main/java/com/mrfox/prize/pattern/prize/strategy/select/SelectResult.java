package com.mrfox.prize.pattern.prize.strategy.select;

import lombok.AllArgsConstructor;
import lombok.Data;

/*****
 * 中奖结果
 * @author     : MrFox
 * @date       : 2020-01-28 22:23
 * @description:
 * @version    :
 ****/
@Data
@AllArgsConstructor
public class SelectResult {

    /**
     * 是否中奖
     * */
    private Boolean isWin;

    /**
     * 奖项Id
     * */
    private Long awardId;

}
