package com.example.cryptosim.repository

import com.example.cryptosim.entity.Portfolio
import com.example.cryptosim.entity.UserAccount
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PortfolioRepository: JpaRepository<Portfolio, Long> {
    fun findByUser(user: UserAccount, pageable: Pageable): Page<Portfolio>
}