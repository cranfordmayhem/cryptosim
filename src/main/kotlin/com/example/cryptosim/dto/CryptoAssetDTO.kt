package com.example.cryptosim.dto

import java.math.BigDecimal
import java.time.LocalDate

data class CryptoAssetResponse(
    val id: String,
    val symbol: String,
    val name: String,
    val priceUsd: BigDecimal,
    val recordedAt: LocalDate
)