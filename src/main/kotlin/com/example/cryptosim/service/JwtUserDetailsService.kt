package com.example.cryptosim.service

import com.example.cryptosim.exception.UserNotFoundException
import com.example.cryptosim.repository.UserAccountRepository
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(private val userAccountRepo: UserAccountRepository): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userAccountRepo.findByEmail(username) ?: throw UserNotFoundException(username)
        return User.builder()
            .username(user.email)
            .password(user.passwordHash)
            .roles(user.role.name)
            .build()
    }
}