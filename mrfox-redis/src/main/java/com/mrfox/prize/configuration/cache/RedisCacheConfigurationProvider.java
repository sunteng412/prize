package com.mrfox.prize.configuration.cache;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.mrfox.prize.configuration.read.CacheInitConfigEntity;
import com.mrfox.prize.configuration.read.ReadAppJsonConfig;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RedisCacheConfigurationProvider extends CacheConfig.RedisCacheConfigurationProvider {

    @Resource
    private ReadAppJsonConfig readAppJsonConfig;

    @Override
    protected void initConfigs() {
        this.configs = Maps.newHashMap();
//        //获取配置
        List<CacheInitConfigEntity> cacheInitConfigEntities = readAppJsonConfig.readJsonToMap();

        //填充配置
        cacheInitConfigEntities.stream().forEach(e->{
            try {
                this.configs.put(e.getKeyName(), new Pair<>(Duration.ofMinutes(e.getExpireDate()), JacksonHelper.genJavaType(Class.forName(e.getClassName()))));
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        //this.configs.put("cache_key_1", new Pair<>(Duration.ofDays(5), JacksonHelper.genMapType(HashMap.class,String.class,Object.class)));
    }

}