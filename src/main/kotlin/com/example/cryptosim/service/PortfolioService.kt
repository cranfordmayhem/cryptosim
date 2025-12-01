package com.example.cryptosim.service

import com.example.cryptosim.dto.*
import com.example.cryptosim.entity.*
import com.example.cryptosim.exception.*
import com.example.cryptosim.repository.*
import org.slf4j.LoggerFactory
import org.springframework.data.domain.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PortfolioService(
    private val portfolioRepo: PortfolioRepository,
    private val userAccountRepo: UserAccountRepository
) {
    private val logger = LoggerFactory.getLogger(PortfolioService::class.java)

    fun create(portfolio: PortfolioRequest, userEmail: String): PortfolioResponse {
        val user = userAccountRepo.findByEmail(userEmail)
            ?: throw UserNotFoundException(userEmail)

        return portfolioRepo.save(portfolio.toEntity(user)).toResponse().also{
            logger.info("Portfolio ${it.id} created successfully")
        }
    }

    @Transactional(readOnly=true)
    fun retrieve(pageable: Pageable): Page<PortfolioResponse> =
        portfolioRepo.findAll(pageable).apply {
            logger.debug("Retrieving all portfolios")
        }.map { it.toResponse() }.also {
            logger.info("Portfolios retrieved successfully")
        }

    @Transactional(readOnly=true)
    fun retrieveByUser(userEmail: String, pageable: Pageable): Page<PortfolioResponse> {
        val user = userAccountRepo.findByEmail(userEmail)
            ?: throw UserNotFoundException(userEmail)
        return portfolioRepo.findByUser(user, pageable).map { it.toResponse() }.also {
            logger.info("Portfolios for $userEmail retrieved successfully")
        }
    }

    fun retrieveById(id: Long, userEmail: String): PortfolioResponse =
        portfolioRepo.findByIdOrNull(id)?.apply {
            val user = userAccountRepo.findByEmail(userEmail)
                ?: throw UserNotFoundException(userEmail)
            if(user.email != userEmail || user.role != Role.ADMIN)
                throw UnauthorizedException("access", id)

        }?.toResponse().also{ logger.info("Portfolio Details retrieved successfully") }
            ?: throw IdNotFoundException(id)

    fun update(id: Long, portfolio: PortfolioRequest, userEmail: String): PortfolioResponse =
        portfolioRepo.findByIdOrNull(id)?.let { existingPortfolio ->
            logger.debug("Updating profile $id")
            val user = userAccountRepo.findByEmail(userEmail)
                ?: throw UserNotFoundException(userEmail)

            if(existingPortfolio.user.email != userEmail || user.role != Role.ADMIN)
                throw UnauthorizedException("update", id)

            val newPortfolioData = portfolio.toEntity(existingPortfolio.user)
            val updatedPortfolio = existingPortfolio.copy(
                name = newPortfolioData.name
            )
            portfolioRepo.save(updatedPortfolio).toResponse().also{
                logger.info("Portfolio updated successfully")
            }
        } ?: throw IdNotFoundException(id)

    fun delete(id: Long, userEmail: String): Unit? =
        portfolioRepo.findByIdOrNull(id).apply {
            val user = userAccountRepo.findByEmail(userEmail)
                ?: throw UserNotFoundException(userEmail)
            if(this?.user?.email != userEmail || user.role != Role.ADMIN )
                throw UnauthorizedException("delete", id)
        }?.let { portfolioRepo.deleteById(id) }?.also { logger.info("Portfolio deleted successfully") }
            ?: throw IdNotFoundException(id)
}