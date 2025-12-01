package com.example.cryptosim.controller

import com.example.cryptosim.dto.*
import com.example.cryptosim.service.UserProfileService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*
@Validated
@RestController
@RequestMapping("/profile")
class UserProfileController(
    private val userProfileService: UserProfileService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun add(@RequestBody profile: UserProfileRequest): UserProfileResponse {
        val email = SecurityContextHolder.getContext().authentication.name
            ?: throw RuntimeException("NO ACCOUNT FOUND")
        return userProfileService.create(profile, email)
    }

    @GetMapping
    fun retrieveAll(@PageableDefault pageable: Pageable) =
        userProfileService.retrieve(pageable)

    @GetMapping("/{id}")
    fun retrieveById(@PathVariable id: Long): UserProfileResponse {
        val email = SecurityContextHolder.getContext().authentication.name
            ?: throw RuntimeException("NO ACCOUNT FOUND")
        return userProfileService.retrieveById(id, email)
    }


    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody profileInput: UserProfileRequest): UserProfileResponse {
        val email = SecurityContextHolder.getContext().authentication.name
            ?: throw RuntimeException("NO ACCOUNT FOUND")
        return userProfileService.update(id, profileInput, email)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Unit? {
        val email = SecurityContextHolder.getContext().authentication.name
            ?: throw RuntimeException("NO ACCOUNT FOUND")
        return userProfileService.delete(id, email)
    }
}