package com.rick.financial_web.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

// 创建一个自定义的RedisTemplate Bean，用于与Redis进行交互
@Configuration
public class RedisTemplateConfig {

        @Bean
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory ) {
            // 创建 RedisTemplate 实例
            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

            // 设置 Redis 连接工厂
            redisTemplate.setConnectionFactory(redisConnectionFactory);

            // 设置 Redis 中的键序列化器，使用 StringRedisSerializer
            redisTemplate.setKeySerializer(new StringRedisSerializer());

            // 设置 Redis 中的值序列化器，使用 GenericJackson2JsonRedisSerializer，
            // 这允许您将 Java 对象以 JSON 格式存储在 Redis 中
            redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

            // 设置 Redis 中哈希键（Key）的序列化器，使用 StringRedisSerializer
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());

            // 设置 Redis 中哈希值（Value）的序列化器，GenericJackson2JsonRedisSerializer不用传对象进去
            redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

            // 调用 afterPropertiesSet 方法，初始化 RedisTemplate
            redisTemplate.afterPropertiesSet();

            // 返回配置好的 RedisTemplate
            return redisTemplate;
        }

}
