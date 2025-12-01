package com.example.cryptosim.dto

data class PortfolioRequest(
    val name: String? = null
)

data class PortfolioResponse(
    val id: Long,
    val name: String
)