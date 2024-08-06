package com.jnd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

/**
 * @Classname RedisCacheConfig
 * @Description redis cache缓存配置类
 * @Version 1.0.0
 * @Date 2024/8/5 21:57
 * @Created by jnd
 */
@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
        //创建redis缓存配置类
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        //进行redis配置
        redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()))
                .entryTtl(Duration.ofDays(7)) //设置redis值的过期时间默认为7天
                .disableCachingNullValues(); // 禁止redis的value为空


        return redisCacheConfiguration;
    }
}
