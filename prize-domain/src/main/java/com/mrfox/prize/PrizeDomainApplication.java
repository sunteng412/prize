package com.mrfox.prize;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
//开启注册发现
@EnableDiscoveryClient
//开启dubbo
@EnableDubbo
//设置mybatis扫描bean目录
@MapperScan(basePackages={"com.mrfox.prize.mapper"})
//设置重试
@EnableRetry
public class PrizeDomainApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrizeDomainApplication.class, args);
    }

}
