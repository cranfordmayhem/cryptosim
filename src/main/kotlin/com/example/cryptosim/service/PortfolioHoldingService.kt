package com.example.cryptosim.service

import com.example.cryptosim.dto.*
import com.example.cryptosim.entity.Role
import com.example.cryptosim.exception.*
import com.example.cryptosim.repository.*
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PortfolioHoldingService(
    private val holdingRepo: PortfolioHoldingRepository,
    private val portfolioRepo: PortfolioRepository,
    private val cryptoAssetRepo: CryptoAssetRepository
) {

    private val logger = LoggerFactory.getLogger(PortfolioHoldingService::class.java)

    fun addHolding(portfolioId: Long, request: PortfolioHoldingRequest, userEmail: String): PortfolioHoldingResponse {
        val portfolio = portfolioRepo.findByIdOrNull(portfolioId)
            ?: throw IdNotFoundException(portfolioId)

        // Authorization: owner or admin
        if (portfolio.user.email != userEmail && portfolio.user.role != Role.ADMIN) {
            throw UnauthorizedException("add holding", portfolioId)
        }

        val asset = cryptoAssetRepo.findById(request.assetId)
            .orElseThrow{ AssetIdNotFoundException(request.assetId) }

        val holding = request.toEntity(portfolio, asset)
        return holdingRepo.save(holding).toResponse()
    }

    fun getUserPortfolioHoldings(portfolioId: Long, userId: Long): List<PortfolioHoldingResponse> {
        return holdingRepo
            .findByPortfolioIdAndPortfolioUserId(portfolioId, userId)
            .map { it.toResponse() }
    }


}