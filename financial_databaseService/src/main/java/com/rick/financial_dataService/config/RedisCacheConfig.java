package com.rick.financial_dataService.config;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))//GenericJackson2JsonRedisSerializer不用传对象进去
                //3600秒过期
                .entryTtl(Duration.ofSeconds(3600))
                //方法用于在缓存配置中禁用缓存空值，使得缓存不会存储 null 值
                .disableCachingNullValues();
                //和cacheName冲突
//                .computePrefixWith(cacheName -> "Prefix:");
                //前面一个是全局name，后面一个是key
//                .computePrefixWith(cacheName -> "empPrefix:" + cacheName + "haha");
                //全局前缀,在cacheName前加上
//                .prefixCacheNameWith("globalPrefix:");


        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

}
