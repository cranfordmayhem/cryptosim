package com.example.cryptosim.service

import com.example.cryptosim.dto.*
import com.example.cryptosim.repository.CryptoAssetRepository
import com.example.cryptosim.repository.specification.CryptoAssetSpecifications
import org.slf4j.LoggerFactory
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CryptoAssetService(
    private val assetRepo: CryptoAssetRepository
) {

    private val logger = LoggerFactory.getLogger(CryptoAssetService::class.java)

    fun retrieve(
        symbol: String?,
        name: String?,
        minPrice: BigDecimal?,
        maxPrice: BigDecimal?,
        pageable: Pageable
    ): Page<CryptoAssetResponse>{
        val filter = AssetFilter(symbol, name, minPrice, maxPrice)

        return assetRepo.findAll(CryptoAssetSpecifications.filter(filter), pageable)
            .map { it.toResponse() }.also{
                logger.info("Assets with prices retrieved successfully")
            }
    }

    fun retrieveById(id: String): CryptoAssetResponse{
        val asset = assetRepo.findById(id)
            .orElseThrow { RuntimeException("Crypto Asset with id $id not found") }

        val latestPrice = asset.cryptoPriceHistory.maxByOrNull{ it.recordedAt }
            ?: throw RuntimeException("No price history for asset $id")

        return CryptoAssetResponse(
            id = asset.id,
            symbol = asset.symbol,
            name = asset.name,
            priceUsd = latestPrice.priceUsd,
            recordedAt = latestPrice.recordedAt
        )
    }
}