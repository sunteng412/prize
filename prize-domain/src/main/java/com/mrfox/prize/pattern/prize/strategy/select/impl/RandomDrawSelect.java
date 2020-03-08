package com.mrfox.prize.pattern.prize.strategy.select.impl;

import com.mrfox.prize.pattern.prize.strategy.select.RandomDrawRegion;
import com.mrfox.prize.pattern.prize.strategy.select.RandomDrawRegionCache;
import com.mrfox.prize.pattern.prize.strategy.select.SelectResult;
import com.mrfox.prize.util.RedisUtil;
import com.mrfox.prize.utils.RedisKeyUtil;
import com.mrfox.prize.vo.ActivePrizeDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/*****
 * 随机抽奖实现
 * @author     : MrFox
 * @date       : 2020-01-29 21:06
 * @description:
 * @version    :
 ****/
@Slf4j
@Component
public class RandomDrawSelect extends AbstractDrawSelect {

    /**
     * 随机值
     */
    private final Random random = new Random();

    public static final String NAME = "randomDrawSelect";

    /**
     * 比值最多精确达到二位小数
     */
    public static final int TOTAL_SIZE = 10000;

    private final BigDecimal bigDecimal = new BigDecimal(100);

    /*****
     * 实际执行抽奖策略
     * @param
     * @return
     * @description:
     ****/
    @Override
    protected SelectResult doSelect(List<ActivePrizeDetailVo> activePrizeDetailVoList) {
        log.info("[RandomDrawSelect.doSelect]activePrizeDetailVoList:{} ", activePrizeDetailVoList);
        //随机生成一个数值
        Integer randomInt = random.nextInt(TOTAL_SIZE);
        RandomDrawRegionCache randomDrawRegionCache = getRandomDrawRegionCache(activePrizeDetailVoList);
        Integer notWin = randomDrawRegionCache.getNotWin();
        List<RandomDrawRegion> randomDrawRegionList = randomDrawRegionCache.getRandomDrawRegionList();

        //不会中奖
        if (randomInt >= notWin) {
            return new SelectResult(Boolean.FALSE, null);
        } else {
            //会中奖的使用二分查找法
            Long search = binarySearch(randomDrawRegionList, randomInt);
            return search > -1 ? new SelectResult(Boolean.TRUE, search) : new SelectResult(Boolean.FALSE, null);
        }
    }


    //@Cacheable("activePrizeDetailVoList")--同方法调用不走缓存,因为基于aop代理实现
    public RandomDrawRegionCache getRandomDrawRegionCache(List<ActivePrizeDetailVo> activePrizeDetailVoList) {
//        String redisKey = RedisKeyUtil.getRedisKey("RandomDrawRegionCache", activePrizeDetailVoList.get(0).getActiveId());
//        String randomDrawRegionCacheStr = (String) redisUtil.get(redisKey);
//        if(StringUtils.isBlank(randomDrawRegionCacheStr)){
//
//        }else {
//            return re
//        }
        log.info("[RandomDrawSelect.getRandomDrawRegionCache]activePrizeDetailVoList:{}", activePrizeDetailVoList);
        RandomDrawRegionCache randomDrawRegionCache = new RandomDrawRegionCache();

        List<RandomDrawRegion> randomDrawRegionList = new ArrayList<>(activePrizeDetailVoList.size());

        //不会中奖的比例-也作为计算偏移量
        AtomicReference<Integer> notWin = new AtomicReference<>(0);

        List awardsId = new ArrayList();

        //获取比例最大值(即不会中奖的概率)
        activePrizeDetailVoList.forEach(e -> {
            Integer probability = e.getProbability().multiply(bigDecimal).intValue();

            if (!awardsId.contains(e.getAwardId())) {
                //换算后
                RandomDrawRegion randomDrawRegion = new RandomDrawRegion();
                randomDrawRegion.setProbability(probability);
                randomDrawRegion.setProbabilityStart(notWin.get());
                randomDrawRegion.setProbabilityEnd(notWin.get() + probability);
                randomDrawRegion.setAwardId(e.getAwardId());
                randomDrawRegionList.add(randomDrawRegion);
                awardsId.add(e.getAwardId());
                //设置不会中奖的比例
                notWin.updateAndGet(v -> v + probability);
            }

        });
        randomDrawRegionCache.setNotWin(notWin.get());
        randomDrawRegionCache.setRandomDrawRegionList(randomDrawRegionList);
        return randomDrawRegionCache;
    }

    /*****
     * 二分查找法
     * @param randomDrawRegionList 概率
     * @param randomInt 随机值
     * @return {@link Long} 奖项id
     * @description:
     ****/
    Long binarySearch(List<RandomDrawRegion> randomDrawRegionList, Integer randomInt) {
        int low = 0;
        int high = randomDrawRegionList.size() - 1;
        while (low <= high) {
            int mid = (high + low) >>> 1;
            RandomDrawRegion randomDrawRegion = randomDrawRegionList.get(mid);
            if (randomDrawRegion.getProbabilityEnd() > randomInt
                    && randomDrawRegion.getProbabilityStart() < randomInt) {
                return randomDrawRegion.getAwardId();
            } else if (randomDrawRegion.getProbabilityEnd() > randomInt) {
                high = mid - 1;
            } else if (randomDrawRegion.getProbabilityStart() < randomInt) {
                low = mid + 1;
            }

        }
        return -1L;
    }

}
