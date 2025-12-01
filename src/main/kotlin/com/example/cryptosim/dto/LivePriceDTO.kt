package com.example.cryptosim.dto

import java.time.Instant

data class LivePriceResponse(
    val assetId: String,
    val priceUsd: Double,
    val lastUpdated: Instant
)