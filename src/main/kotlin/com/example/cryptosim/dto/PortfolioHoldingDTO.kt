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