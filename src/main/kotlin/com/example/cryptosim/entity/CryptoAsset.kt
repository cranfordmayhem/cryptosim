package com.example.cryptosim.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name="crypto_asset")
data class CryptoAsset(
    @Id
    val id: String,

    @Column(nullable=false)
    val symbol: String,

    @Column(nullable=false)
    val name: String,

    @OneToMany(mappedBy = "asset", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val cryptoPriceHistory: List<CryptoPriceHistory> = mutableListOf()
) : Auditor()