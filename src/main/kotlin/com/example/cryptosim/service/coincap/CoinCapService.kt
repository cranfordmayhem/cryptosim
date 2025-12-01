package com.example.cryptosim.service.coincap

import com.example.cryptosim.dto.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient

@Service
class CoinCapService(
    private val restTemplate: RestTemplate = RestTemplate(),
    @Value("\${coincap.base-url}") private val baseUrl: String,
    @Value("\${coincap.api-key}") private val apiKey: String,
) {

    private val client: WebClient = WebClient.builder()
        .baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer $apiKey")
        .build()


    fun getAllAssets(): List<CoinCapAsset> {
        val response = client.get()
            .uri("/assets")
            .retrieve()
            .bodyToMono(CoinCapResponse::class.java)
            .block() ?: throw RuntimeException("Failed to fetch assets")

        return response.data
    }

    fun getAssetPrice(assetId: String): Double {
        val response = client.get()
            .uri("/assets/{id}", assetId)
            .retrieve()
            .bodyToMono(CoinCapSingleAssetResponse::class.java)
            .block() ?: throw RuntimeException("Failed to fetch asset price")

        return response.data.priceUsd.toDouble()
    }
}