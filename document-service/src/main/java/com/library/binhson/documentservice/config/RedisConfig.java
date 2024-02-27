package com.library.binhson.documentservice.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.BatchStrategies;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static java.util.Collections.singletonMap;
import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory){
        return RedisCacheManager
                .builder(RedisCacheWriter
                        .lockingRedisCacheWriter(connectionFactory, BatchStrategies
                                .scan(1000)))
                .cacheDefaults(defaultCacheConfig(Thread.currentThread().getContextClassLoader()))
                .withInitialCacheConfigurations(singletonMap("predefined", defaultCacheConfig().disableCachingNullValues()))
                .transactionAware()
                .build();
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
        return RedisCacheConfiguration.defaultCacheConfig().computePrefixWith(cacheName -> "(° _ᴥ_ °)"+ cacheName)
                .entryTtl(Duration.ofSeconds(1))
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}
