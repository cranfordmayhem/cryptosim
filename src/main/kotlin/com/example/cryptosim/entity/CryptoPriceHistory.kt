package com.example.cryptosim.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name="crypto_price_history")
data class CryptoPriceHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name="asset_id", nullable=false)
    val asset: CryptoAsset,

    @Column(name="price_usd", precision=20, scale=10, nullable=false)
    val priceUsd: BigDecimal,

    @Column(name="recorded_at", nullable=false)
    val recordedAt: LocalDate
) : Auditor()