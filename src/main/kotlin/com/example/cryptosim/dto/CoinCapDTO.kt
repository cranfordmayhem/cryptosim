package com.example.cryptosim.dto

data class CoinCapAsset(
    val id: String,
    val symbol: String,
    val name: String
)

data class CoinCapResponse(
    val data: List<CoinCapAsset>
)

data class CoinCapSingleAssetResponse(
    val data: CoinCapSingleAsset
)

data class CoinCapSingleAsset(
    val id: String,
    val symbol: String,
    val name: String,
    val priceUsd: String,
)