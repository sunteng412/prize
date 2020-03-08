package com.mrfox.prize.api;


import com.mrfox.prize.dto.PrizeDrawDto;

import java.util.List;

public interface TestDubboApi {
    String hello(String didi);

    PrizeDrawDto testRedis(Integer i);

     List<Integer> testRedisCacheList(List<Integer> list);

    String testRedisTransaction();

}
