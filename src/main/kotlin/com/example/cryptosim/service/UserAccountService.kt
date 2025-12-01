package com.example.cryptosim.service

import com.example.cryptosim.dto.*
import com.example.cryptosim.entity.*
import com.example.cryptosim.exception.*
import com.example.cryptosim.repository.*
import org.slf4j.LoggerFactory
import org.springframework.data.domain.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service



@Service
class UserAccountService(
    private val userAccountRepo: UserAccountRepository,
    private val passwordEncoder: PasswordEncoder
) {
    private val logger = LoggerFactory.getLogger(UserAccountService::class.java)

    fun register(user: UserAccountRequest): UserAccountResponse? {
        logger.debug("Creating User Account")
        if(userAccountRepo.existsByEmail(user.email)) throw FieldTakenException(user.email)

        val hashedPassword = passwordEncoder.encode(user.password)

        val newUser = UserAccountRequest(
            email = user.email,
            password = hashedPassword,
            role = Role.USER
        )

        return userAccountRepo.save(newUser.toEntity()).toResponse().also {
            logger.info("User Account ${it.id} created successfully")
        }
    }

    fun retrieve(pageable: Pageable): Page<UserAccountResponse> =
        userAccountRepo.findAll(pageable).apply {
            logger.debug("Retrieving all users")
        }.map { it.toResponse() }.also{ logger.info("users retrieved successfully") }

    fun update(id: Long, user: UserAccountRequest): UserAccountResponse =
        userAccountRepo.findByIdOrNull(id)?.let { existingUser ->
            logger.debug("Updating existing user: ${existingUser.id}")
            if(user.email != existingUser.email) {
                if(userAccountRepo.existsByEmailAndIdNot(user.email, id)) {
                    throw FieldTakenException(user.email)
                }
            }

            val input = user.toEntity()

            val updatedUser = existingUser.copy(
                email= input.email,
                passwordHash = if (input.passwordHash.isNotBlank())
                    passwordEncoder.encode(user.password) else existingUser.passwordHash,
            )
            userAccountRepo.save(updatedUser).toResponse().also {
                logger.info("User updated successfully")
            }
        } ?: throw IdNotFoundException(id)

    fun retrieveById(id: Long): UserAccountResponse? =
        userAccountRepo.findByIdOrNull(id)?.apply{
            logger.debug("Retrieving specific user")
        }?.toResponse()?.also { logger.info("user details retrieved successfully") }
            ?: throw IdNotFoundException(id)

    fun retrieveByEmail(email: String): UserAccountResponse {
        val user = userAccountRepo.findByEmail(email)
            ?: throw RuntimeException("User not found")

        return user.toResponse()
    }
}