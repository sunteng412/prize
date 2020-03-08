package com.mrfox.prize.controller;

import com.mrfox.prize.api.TestDubboApi;
import com.mrfox.prize.dto.PrizeDrawDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestDubboController {

    @Reference(check = false)
    TestDubboApi testDubboApi;

    @GetMapping("/dubbo")
    public String test() {
        log.info("嘿嘿111");
        String result = testDubboApi.hello("didi");
        return "Return : " + result;
    }


    @GetMapping("/redisCache")
    public PrizeDrawDto testRedis() {
        PrizeDrawDto x = testDubboApi.testRedis(1);
        System.out.println(x);
        return x;
    }

    @GetMapping("/testRedisCacheList")
    public List<Integer> testRedisCacheList(Integer x) {
        return testDubboApi.testRedisCacheList(new ArrayList<Integer>() {
            {
                add(x);
            }
        });
    }

    @GetMapping("/redisTransaction")
    public String testRedisTransaction() {
        testDubboApi.testRedisTransaction();
        return "success";
    }
}
