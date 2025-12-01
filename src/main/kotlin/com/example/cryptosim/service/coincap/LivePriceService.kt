package com.example.cryptosim.service.coincap

import com.example.cryptosim.dto.LivePriceResponse
import com.github.benmanes.caffeine.cache.Cache
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.Instant
import kotlin.collections.get

@Service
class LivePriceService(
    @Value("\${coincap.base-url}") private val baseUrl: String,
    @Value("\${coincap.api-key}") private val apiKey: String,
    private val priceCache: Cache<String, LivePriceResponse>
) {
    private val client = WebClient.builder()
        .baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer $apiKey")
        .build()

    fun getLivePrice(assetId: String): LivePriceResponse {
        priceCache.getIfPresent(assetId)?.let { return it }

        val response = client.get()
            .uri("/assets/$assetId")
            .retrieve()
            .bodyToMono(Map::class.java)
            .block() ?: throw RuntimeException("Failed to fetch price for $assetId")

        val data = response["data"] as Map<*, *>
        val price = (data["priceUsd"] as String).toDouble()

        val dto = LivePriceResponse(
            assetId = assetId,
            priceUsd = price,
            lastUpdated = Instant.now()
        )

        priceCache.put(assetId, dto)

        return dto
    }

}