package com.example.cryptosim.dto

import java.math.BigDecimal

data class PortfolioHoldingRequest(
    val assetId: String,
    val amount: BigDecimal
)

data class PortfolioHoldingResponse(
    val id: Long,
    val assetId: String,
    val amount: BigDecimal
)

data class PricedHoldingRequest(
    val assetId: String,
    val amount: BigDecimal
)

data class PricedHoldingResponse(
    val id: Long,
    val assetId: String,
    val amount: BigDecimal,
    val price: BigDecimal,
    val totalValue: BigDecimal
)