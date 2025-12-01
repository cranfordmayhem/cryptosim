package com.example.cryptosim.repository

import com.example.cryptosim.entity.PortfolioHolding
import com.example.cryptosim.entity.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserProfileRepository: JpaRepository<UserProfile, Long>{
    fun findByUserId(userId: Long): UserProfile
}