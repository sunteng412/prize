package com.mrfox.prize.api;

import com.mrfox.prize.dto.PrizeDrawDto;
import com.mrfox.prize.resonse.Response;
import com.mrfox.prize.vo.PrizeDrawVo;

/*****
 * 用户抽奖业务控制层
 * @author     : MrFox
 * @date       : 2020-01-24 22:42
 * @description:
 * @version    :
 ****/
public interface PrizeDrawApi {
    Response<PrizeDrawVo> prizeDraw(PrizeDrawDto prizeDrawDto) throws Exception;
}
