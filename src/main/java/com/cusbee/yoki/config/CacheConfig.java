package com.cusbee.yoki.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("order"),
                new ConcurrentMapCache("order_dishes"),
                new ConcurrentMapCache("dish"),
                new ConcurrentMapCache("all_dishes"),
                new ConcurrentMapCache("av_dishes"),
                new ConcurrentMapCache("category_dishes"),
                new ConcurrentMapCache("ingredient"),
                new ConcurrentMapCache("ingredients")));
        return cacheManager;
    }
}
