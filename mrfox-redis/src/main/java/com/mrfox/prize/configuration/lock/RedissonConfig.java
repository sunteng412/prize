package com.mrfox.prize.configuration.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
/*****
 * redission单机版锁
 * @author     : MrFox
 * @date       : 2020-01-26 20:30
 * @description:
 * @version    :
 ****/
@Configuration
public class RedissonConfig {
 
    @Autowired
    private RedisProperties redisProperties;
 
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        String redisUrl = String.format("redis://%s:%s",redisProperties.getHost()+"",redisProperties.getPort()+"");
        config.useSingleServer().setAddress(redisUrl);
        config.useSingleServer().setDatabase(0);
        return Redisson.create(config);
    }
 
}