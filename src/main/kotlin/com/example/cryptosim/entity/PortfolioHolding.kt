package com.example.cryptosim.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name="portfolio_holding")
data class PortfolioHolding(
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="portfolio_id", nullable=false)
    val portfolio: Portfolio,

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="asset_id", nullable=false)
    val asset: CryptoAsset,

    @Column(nullable=false, precision=30, scale=10)
    val amount: BigDecimal
) : Auditor()