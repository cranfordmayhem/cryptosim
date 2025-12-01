package com.example.cryptosim.repository

import com.example.cryptosim.entity.CryptoPriceHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface CryptoPriceHistoryRepository: JpaRepository<CryptoPriceHistory, Long> {
    fun findByAssetIdOrderByRecordedAtDesc(assetId: String): List<CryptoPriceHistory>
    fun findByAssetIdAndRecordedAt(assetId: String, recordedAt: LocalDate): CryptoPriceHistory?
}