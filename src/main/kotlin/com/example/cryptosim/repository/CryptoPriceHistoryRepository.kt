package com.example.cryptosim.repository

import com.example.cryptosim.entity.CryptoPriceHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface CryptoPriceHistoryRepository: JpaRepository<CryptoPriceHistory, Long> {
    fun findByAssetIdOrderByRecordedAtDesc(assetId: String): List<CryptoPriceHistory>
    fun findByAssetIdAndRecordedAt(assetId: String, recordedAt: LocalDate): CryptoPriceHistory?

    @Query("""
        SELECT c1 FROM CryptoPriceHistory c1
        WHERE c1.recordedAt = (
            SELECT MAX(c2.recordedAt)
            FROM CryptoPriceHistory c2 
            WHERE c2.asset = c1.asset
        )
    """)
    fun findLatestPerAsset(): List<CryptoPriceHistory>
}