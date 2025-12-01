package com.example.cryptosim.repository

import com.example.cryptosim.entity.CryptoAsset
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface CryptoAssetRepository: JpaRepository<CryptoAsset, String>, JpaSpecificationExecutor<CryptoAsset>