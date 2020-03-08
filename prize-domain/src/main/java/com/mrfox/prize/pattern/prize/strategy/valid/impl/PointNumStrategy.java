package com.mrfox.prize.pattern.prize.strategy.valid.impl;

import com.mrfox.prize.constants.PrizeErrorCode;
import com.mrfox.prize.dto.PrizeDrawDto;
import com.mrfox.prize.mapper.UserMapper;
import com.mrfox.prize.model.User;
import com.mrfox.prize.pattern.prize.strategy.valid.PrizeDrawRequest;
import com.mrfox.prize.pattern.prize.strategy.valid.Strategy;
import com.mrfox.prize.pattern.prize.strategy.valid.ValidStrategy;
import com.mrfox.prize.resonse.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/*****
 * 判断积分数
 * @author     : MrFox
 * @date       : 2020-01-27 15:53
 * @description:
 * @version    :
 ****/
@Strategy(modelName =PointNumStrategy.NAME)
@Component
@Slf4j
public class PointNumStrategy implements ValidStrategy {

    static final String NAME = "pointNum";

    @Resource
    private UserMapper userMapper;


    /*****
     * 判断积分是否足够
     * @param
     * @return
     * @description:
     ****/
    public Response valid(PrizeDrawRequest prizeDrawRequest) {
        User user = prizeDrawRequest.getUser();
        Integer needPointNum = prizeDrawRequest.getPrizeDrawDetailVo().getNeedPointNum();
        if(Objects.isNull(user) || (needPointNum > (user.getPoint()- user.getRealPoint()))){
            return Response.no(400, PrizeErrorCode.POINT_IS_NOT_ENOUGH.code(),PrizeErrorCode.POINT_IS_NOT_ENOUGH.errMsg());
        }else {
            //扣除用户积分数--考虑使用disruptor
            Integer integer = userMapper.updateRealPointById(user.getId(), needPointNum);
            if(integer>0){
                //扣减成功
                log.info("[PointNumStrategy.valid] reduce point ,user:{},needPointNum:{}",user,needPointNum);
            }
            return null;
        }
    }


    /*****
     * 构建查询对象
     * @param prizeDrawDto 抽奖请求
     * @param prizeDrawRequest 抽奖请求信息封装
     * @return
     * @description:
     ****/
    @Override
    public void buildValid(PrizeDrawDto prizeDrawDto, PrizeDrawRequest prizeDrawRequest) {
        //查询用户积分
        User user = userMapper.selectById(prizeDrawDto.getUserId());
        prizeDrawRequest.setUser(user);
        prizeDrawRequest.setError(valid(prizeDrawRequest));
    }
}
