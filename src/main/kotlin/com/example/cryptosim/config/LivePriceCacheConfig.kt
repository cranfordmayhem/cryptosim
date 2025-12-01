package com.example.cryptosim.config

import com.example.cryptosim.dto.LivePriceResponse
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class LivePriceCacheConfig {

    @Bean
    fun priceCache() = Caffeine.newBuilder()
        .expireAfterWrite(2, TimeUnit.MINUTES)
        .maximumSize(500)
        .build<String, LivePriceResponse>()
}