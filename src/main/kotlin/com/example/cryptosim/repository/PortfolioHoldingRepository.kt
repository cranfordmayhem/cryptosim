package com.example.cryptosim.repository

import com.example.cryptosim.entity.PortfolioHolding
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PortfolioHoldingRepository : JpaRepository<PortfolioHolding, Long> {

    fun findByPortfolioUserId(userId: Long): List<PortfolioHolding>

    // Fetch as list
    fun findByPortfolioIdAndPortfolioUserId(portfolioId: Long, userId: Long): List<PortfolioHolding>

    // Fetch as paged
    fun findByPortfolioIdAndPortfolioUserId(portfolioId: Long, userId: Long, pageable: Pageable): Page<PortfolioHolding>
}
