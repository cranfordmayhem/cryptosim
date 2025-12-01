package com.example.cryptosim.dto

import java.math.BigDecimal

data class AssetFilter(
    val symbol: String? = null,
    val name: String? = null,
    val minPrice: BigDecimal? = null,
    val maxPrice: BigDecimal? = null
)