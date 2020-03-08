package com.mrfox.prize.manager;

import com.google.common.base.Throwables;
import com.mrfox.prize.dto.PrizeDrawDto;
import com.mrfox.prize.mapper.ActiveMapper;
import com.mrfox.prize.util.RedisUtil;
import com.mrfox.prize.utils.BeanMapUtils;
import com.mrfox.prize.utils.RedisKeyUtil;
import com.mrfox.prize.vo.ActivePrizeDetailVo;
import com.mrfox.prize.vo.PrizeDrawDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/*****
 * 抽奖活动管理层
 * @author     : MrFox
 * @date       : 2020-01-25 23:09
 * @description:
 * @version    :
 ****/
@Component
@Slf4j
public class PrizeDrawManager {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private ActiveMapper activeMapper;

    /*****
     * 查询活动信息
     * @param
     * @return
     * @description:
     ****/
    public PrizeDrawDetailVo selectActiveDetail(PrizeDrawDto prizeDrawDto){
        String redisKey = RedisKeyUtil.getRedisKey(RedisKeyUtil.ACTIVE_KEY, prizeDrawDto.getActiveId());
        //首先去缓存取
        PrizeDrawDetailVo prizeDrawDetailVo = (PrizeDrawDetailVo)redisUtil.get(redisKey);
        if(Objects.isNull(prizeDrawDetailVo)){
            RLock lock = redissonClient.getLock("LOCK_"+redisKey);
            try {
                lock.lock(5, TimeUnit.MINUTES);
                //双重检验锁--防止重复获取
                if(Objects.isNull(prizeDrawDetailVo = BeanMapUtils.mapToBeanByForceReflux(redisUtil.hGetAll(
                        redisKey)
                        , PrizeDrawDetailVo.class,Boolean.TRUE,null))){
                    //查询活动详情
                    prizeDrawDetailVo = activeMapper.selectPrizeDrawDetail(prizeDrawDto.getActiveId());
                    if(Objects.isNull(prizeDrawDetailVo)){
                        prizeDrawDetailVo = new PrizeDrawDetailVo();
                        prizeDrawDetailVo.setIsExists(0);
                        redisUtil.set(redisKey,prizeDrawDetailVo,6,TimeUnit.MINUTES);
                        prizeDrawDetailVo = null;
                    }else {
                        prizeDrawDetailVo.setIsExists(1);
                        redisUtil.set(redisKey,prizeDrawDetailVo);
                    }
                }
            }catch (Exception e){
                log.error("[PrizeDrawManager.selectActiveDetail] e:{}", Throwables.getStackTraceAsString(e));
            }finally {
                assert lock != null;
                lock.unlock();
            }
        }else {
            if(prizeDrawDetailVo.getIsExists().equals(0)){
                prizeDrawDetailVo = null;
            }
        }
        return prizeDrawDetailVo;
    }

    public Boolean reduceAwardNum(Long activeId, Long awardId) {
        String redisKey = RedisKeyUtil.getRedisKey(RedisKeyUtil.ACTIVE_KEY, activeId);
        RLock lock = redissonClient.getLock("LOCK_"+redisKey);
        lock.lock(5,TimeUnit.MINUTES);
        PrizeDrawDetailVo prizeDrawDetailVo = (PrizeDrawDetailVo)redisUtil.get(redisKey);
        List<ActivePrizeDetailVo> activePrizeDetailVoList = prizeDrawDetailVo.getActivePrizeDetailVoList();
        AtomicReference<Boolean> result = new AtomicReference<>(Boolean.TRUE);
        activePrizeDetailVoList.forEach(e->{
            if(e.getAwardId().equals(awardId) && e.getAwardRealNum() > 0 ){
                e.setAwardRealNum(e.getAwardRealNum() - 1);
                e.setPrizeRemainNum(e.getPrizeRemainNum() -1 );
                redisUtil.set(redisKey,prizeDrawDetailVo);
            }else {
                result.set(Boolean.FALSE);
            }
        });
        lock.unlock();
        return result.get();
    }

    /*****
     * 查询活动/奖项详情
     * @param
     * @return
     * @description:
     ****/

}
