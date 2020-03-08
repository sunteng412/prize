package com.mrfox.prize.bean2bean;

import com.mrfox.prize.dto.PrizeDrawRecordDto;
import com.mrfox.prize.model.ActiveDrawRecord;
import com.mrfox.prize.model.UserPointRecord;
import com.mrfox.prize.pattern.prize.strategy.select.SelectResult;
import com.mrfox.prize.pattern.prize.strategy.valid.PrizeDrawRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/*****
 * 实体转换
 * @author     : MrFox
 * @date       : 2019-12-18 16:07
 * @description:
 * @version    :
 ****/
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseMapping {

    /*****
     * -> PrizeDrawRecordDto
     * @param prizeDrawRequest 抽奖信息
     * @param select 中奖信息
     * @return {@link PrizeDrawRecordDto}
     * @description:
     ****/
    @Mapping(source = "prizeDrawRequest.userDrawRecord.lastDrawTime", target = "drawTime")
    @Mapping(source = "prizeDrawRequest.user.id", target = "userId")
    @Mapping(source = "prizeDrawRequest.userDrawRecord.activeId", target = "activeId")
    @Mapping(source = "select.isWin", target = "isWin")
    @Mapping(source = "select.awardId", target = "awardId")
    @Mapping(source = "prizeDrawRequest.prizeDrawDetailVo.needPointNum", target = "usePoint")
    PrizeDrawRecordDto convertToPrizeDrawRecordDto(PrizeDrawRequest prizeDrawRequest, SelectResult select);

    /*****
     * -> UserPointRecord
     * @param prizeDrawRecordDto 积分消费记录
     * @return {@link UserPointRecord}
     * @description:
     ****/
    @Mapping(source = "drawTime", target = "handleTime")
    @Mapping( expression = "java(1)", target = "businessType")
    @Mapping( expression = "java(2)", target = "type")
    @Mapping(source = "orderId", target = "businessNo")
    UserPointRecord convertToUserPointRecord(PrizeDrawRecordDto prizeDrawRecordDto);

    /*****
     * -> ActiveDrawRecord
     * @param prizeDrawRecordDto 积分消费记录
     * @return {@link UserPointRecord}
     * @description:
     ****/
    ActiveDrawRecord convertToActiveDrawRecord(PrizeDrawRecordDto prizeDrawRecordDto);
}
