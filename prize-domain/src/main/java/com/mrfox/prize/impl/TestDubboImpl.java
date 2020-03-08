package com.mrfox.prize.impl;

import com.alibaba.fastjson.JSON;
import com.mrfox.prize.api.TestDubboApi;
import com.mrfox.prize.configuration.read.CacheInitConfigEntity;
import com.mrfox.prize.dto.PrizeDrawDto;
import com.mrfox.prize.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TestDubboImpl implements TestDubboApi {
    @Override
    public String hello(String didi) {
        log.info("嘿嘿11222");
        return "hello" + didi;
    }

    @Resource
    RedisUtil redisUtil;


    private PrizeDrawDto testRedis() {
        PrizeDrawDto prizeDrawDto = new PrizeDrawDto();
        prizeDrawDto.setActiveId(1L);
        prizeDrawDto.setUserId(1L);
        return prizeDrawDto;
    }


    @Override
    @Cacheable(cacheNames = "cache_key", key = "#i", sync = true)
    public PrizeDrawDto testRedis(Integer i) {
        log.info("cache miss");
        return testRedis();
    }

    @Override
    //@Cacheable("testRedisCacheList")
    @Cacheable(value = "DefaultKeyTest", keyGenerator = "simpleKeyGenerator")
    public List<Integer> testRedisCacheList(List<Integer> list) {
        log.info("cache miss");
        return new ArrayList<Integer>(){{
            add(1);
            add(2);
        }};
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String testRedisTransaction(){
        redisUtil.setEnableTransactionSupport(Boolean.TRUE);
        try {
        redisUtil.set("1",1);

        int x = 1/0;

        redisUtil.set("2",2);
        }finally {
            redisUtil.setEnableTransactionSupport(Boolean.FALSE);
        }

        return "success";
    }
}
