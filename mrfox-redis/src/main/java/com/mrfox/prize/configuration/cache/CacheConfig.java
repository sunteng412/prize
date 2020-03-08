package com.mrfox.prize.configuration.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import javafx.util.Pair;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;


@EnableCaching // 启动缓存
@Configuration
@ConditionalOnBean(name = "redisTemplate")
@ConditionalOnProperty(prefix = "mrfox",name = "enable_redis_cache",havingValue = "true")
class CacheConfig extends CachingConfigurerSupport {



    /**
     * redis cache manager     *     * @return
     */
    @Bean
    @Primary
    public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory, ObjectProvider<List<RedisCacheConfigurationProvider>> configurationProvider) {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = Maps.newHashMap();
        List<RedisCacheConfigurationProvider> configurations = configurationProvider.getIfAvailable();
        if (!CollectionUtils.isEmpty(configurations)) {
            for (RedisCacheConfigurationProvider configuration : configurations) {
                redisCacheConfigurationMap.putAll(configuration.resolve());
            }
        }
        //缓存失效默认6分钟
        RedisCacheManager cacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
                .cacheDefaults(resovleRedisCacheConfiguration(Duration.ofSeconds(300), JacksonHelper.genJavaType(Object.class)))
                .withInitialCacheConfigurations(redisCacheConfigurationMap)
                .build();
        return cacheManager;
    }

    private static RedisCacheConfiguration resovleRedisCacheConfiguration(Duration duration, JavaType javaType) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(javaType)))
                .entryTtl(duration);
    }


    /**
     * 配置一个序列器, 将对象序列化为字符串存储, 和将对象反序列化为对象
     */
    @Bean
    public Jackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    public static abstract class RedisCacheConfigurationProvider {
        // key = 缓存名称， value = 缓存时间 和 缓存类型
        protected Map<String, Pair<Duration, JavaType>> configs;

        protected abstract void initConfigs();

        public Map<String, RedisCacheConfiguration> resolve() {
            initConfigs();
            //Assert.notEmpty(configs, "RedisCacheConfigurationProvider 配置不能为空...");
            Map<String, RedisCacheConfiguration> result = Maps.newHashMap();
            configs.forEach((cacheName, pair) -> result.put(cacheName, resovleRedisCacheConfiguration(pair.getKey(), pair.getValue())));
            return result;
        }
    }



}