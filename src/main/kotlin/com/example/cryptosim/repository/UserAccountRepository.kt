package com.example.cryptosim.repository

import com.example.cryptosim.entity.Role
import com.example.cryptosim.entity.UserAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserAccountRepository: JpaRepository<UserAccount, Long> {
    fun findByEmail(email: String): UserAccount?
    fun existsByRole(role: Role): Boolean
    fun existsByEmail(email: String): Boolean
    fun existsByEmailAndIdNot(email: String, id: Long): Boolean
}