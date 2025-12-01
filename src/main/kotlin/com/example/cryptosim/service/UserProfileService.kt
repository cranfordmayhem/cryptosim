package com.example.cryptosim.service

import com.example.cryptosim.dto.*
import com.example.cryptosim.entity.Role
import com.example.cryptosim.exception.*
import com.example.cryptosim.repository.*
import org.slf4j.LoggerFactory
import org.springframework.data.domain.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserProfileService(
    private val userProfileRepo: UserProfileRepository,
    private val userAccountRepo: UserAccountRepository
) {
    private val logger = LoggerFactory.getLogger(UserProfileService::class.java)

    fun create(profile: UserProfileRequest, userEmail: String): UserProfileResponse {
        logger.debug("Creating profile")

        val user = userAccountRepo.findByEmail(userEmail)
            ?: throw UserNotFoundException(userEmail)

        return userProfileRepo.save(profile.toEntity(user)).toResponse().also{
            logger.info("profile ${it.id} created successfully")
        }
    }

    fun retrieve(pageable: Pageable): Page<UserProfileResponse> {
        return userProfileRepo.findAll(pageable).apply {
            logger.debug("Retrieving all profiles")
        }.map { it.toResponse() }.also { logger.info("Profiles retrieved successfully") }
    }

    fun retrieveById(id: Long, userEmail: String): UserProfileResponse =
        userProfileRepo.findByIdOrNull(id)?.apply {
            logger.debug("Retrieving Profile with id $id")
            val user = userAccountRepo.findByEmail(userEmail)
                ?: throw UserNotFoundException(userEmail)
            if(this.user.email != userEmail && user.role != Role.ADMIN)
                throw UnauthorizedException("access", id)
        }?.toResponse().also { logger.info("Profile details retrieved successfully") }
            ?: throw IdNotFoundException(id)

    fun update(id: Long, profile: UserProfileRequest, userEmail: String): UserProfileResponse =
        userProfileRepo.findByIdOrNull(id)?.let { existingProfile ->
            logger.debug("Updating profile $id")
            val user = userAccountRepo.findByEmail(userEmail)
                ?: throw UserNotFoundException(userEmail)

            if(existingProfile.user.email != userEmail && user.role != Role.ADMIN)
                throw UnauthorizedException("update", id)

            val newProfileData = profile.toEntity(existingProfile.user)
            val updatedProfile = existingProfile.copy(
                firstName = newProfileData.firstName,
                lastName = newProfileData.lastName,
                age = newProfileData.age
            )
            userProfileRepo.save(updatedProfile).toResponse().also {
                logger.info("Profile updated successfully")
            }
        } ?: throw IdNotFoundException(id)

    fun delete(id: Long, userEmail: String): Unit? =
        userProfileRepo.findByIdOrNull(id)?.apply {
            logger.debug("Deleting profile")
            val user = userAccountRepo.findByEmail(userEmail)
                ?: throw UserNotFoundException(userEmail)
            if(this.user.email != userEmail && user.role != Role.ADMIN)
                throw UnauthorizedException("delete", id)
        }?.let { userProfileRepo.deleteById(id) }?.also { logger.info("Profile deleted successfully") }
            ?: throw IdNotFoundException(id)
}