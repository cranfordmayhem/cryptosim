package com.example.cryptosim.service.coincap

import com.example.cryptosim.entity.CryptoPriceHistory
import com.example.cryptosim.repository.CryptoAssetRepository
import com.example.cryptosim.repository.CryptoPriceHistoryRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DailyPriceScheduler(
    private val coinCapService: CoinCapService,
    private val assetRepo: CryptoAssetRepository,
    private val historyRepo: CryptoPriceHistoryRepository
) {
    @Scheduled(cron="0 30 8 * * *", zone="Asia/Manila")
    fun updateDailyPrices() {
        val today = LocalDate.now()

        assetRepo.findAll().forEach { asset ->
            if(historyRepo.findByAssetIdAndRecordedAt(asset.id, today) != null)
                return@forEach

            val price = coinCapService.getAssetPrice(asset.id)

            val entry = CryptoPriceHistory(
                asset = asset,
                priceUsd = price.toBigDecimal(),
                recordedAt = today
            )

            historyRepo.save(entry)
        }
    }

}