package com.mrfox.prize.controller;

import com.google.common.base.Throwables;
import com.mrfox.prize.api.PrizeDrawApi;
import com.mrfox.prize.dto.PrizeDrawDto;
import com.mrfox.prize.resonse.ErrorCode;
import com.mrfox.prize.resonse.Response;
import com.mrfox.prize.resonse.ResultStatus;
import com.mrfox.prize.vo.PrizeDrawVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*****
 * 抽奖控制层
 * @author     : MrFox
 * @date       : 2020-01-24 21:59
 * @description:
 * @version    :
 ****/
@Slf4j
@RestController
@RequestMapping("/prize")
public class PrizeController {

    @Reference(check = false,timeout = 30000)
    private PrizeDrawApi prizeDrawApi;

    @PostMapping("/lucky")
    public Response<PrizeDrawVo> prizeDraw(@RequestBody PrizeDrawDto prizeDrawDto){
        log.info("[PrizeController#prizeDraw] prizeDrawDto:{}",prizeDrawDto);
        Response<PrizeDrawVo> response ;
        try {
            response = prizeDrawApi.prizeDraw(prizeDrawDto);
        }catch (Exception e){
            log.error("[PrizeController#prizeDraw] is error ,e:{}", Throwables.getStackTraceAsString(e));
            response = Response.no(ResultStatus.SYSTEM_FAIL.getStatus(),ErrorCode.SYSTEM_ERROR.errMsg());
        }
        return response;
    }


}
