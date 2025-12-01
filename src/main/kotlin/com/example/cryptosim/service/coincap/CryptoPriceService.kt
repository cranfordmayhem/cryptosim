package com.example.cryptosim.service.coincap

import com.example.cryptosim.dto.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CryptoPriceService(
    @Value("\${coincap.api-key}") private val apiKey: String
) {
    private val restTemplate = RestTemplate()
    private val apiUrl = "https://api.coincap.io/v2/assets"

    fun fetchPrices(): List<CoinCapAsset> {
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $apiKey")

        val entity = HttpEntity<String>(headers)

        val response = restTemplate.exchange(
            apiUrl,
            HttpMethod.GET,
            entity,
            CoinCapResponse::class.java
        )
        return response.body?.data ?: emptyList()
    }

    @Scheduled(cron="0 0 0 * * *")
    @CachePut("cryptoPrices")
    fun refreshPrices(): List<CoinCapAsset>{
        return fetchPrices()
    }

    @Cacheable("cryptoPrices")
    fun getCachedPrices(): List<CoinCapAsset> {
        return fetchPrices()
    }
}