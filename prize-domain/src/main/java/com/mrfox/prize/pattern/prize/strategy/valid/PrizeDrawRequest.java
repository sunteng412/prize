package com.mrfox.prize.pattern.prize.strategy.valid;

import com.mrfox.prize.model.User;
import com.mrfox.prize.model.UserDrawRecord;
import com.mrfox.prize.resonse.Response;
import com.mrfox.prize.vo.PrizeDrawDetailVo;
import lombok.Data;

/*****
 * 组合请求实体
 * @author     : MrFox
 * @date       : 2020-01-28 20:44
 * @description:
 * @version    :
 ****/
@Data
public class PrizeDrawRequest {

    /**
     * 错误
     * */
    private Response error;

    /**
     * 用户抽奖记录
     * */
    private UserDrawRecord userDrawRecord;

    /**
     * 抽奖信息
     * */
    private PrizeDrawDetailVo prizeDrawDetailVo;

    /**
     * 用户
     * */
    private User user;
}
