package com.example.cryptosim.service.coincap

import com.example.cryptosim.entity.CryptoAsset
import com.example.cryptosim.repository.CryptoAssetRepository
import org.springframework.stereotype.Service

@Service
class AssetSyncService(
    private val coinCapService: CoinCapService,
    private val assetRepo: CryptoAssetRepository
) {
    fun syncAssets() {
        val externalAssets = coinCapService.getAllAssets()

        externalAssets.forEach { dto ->
            val existing = assetRepo.findById(dto.id)

            if (existing.isEmpty) {
                assetRepo.save(
                    CryptoAsset(
                        id = dto.id,
                        symbol = dto.symbol,
                        name = dto.name
                    )
                )
            }
        }
    }
}