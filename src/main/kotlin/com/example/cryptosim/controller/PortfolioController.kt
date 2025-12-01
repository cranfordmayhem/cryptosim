package com.example.cryptosim.controller

import com.example.cryptosim.dto.*
import com.example.cryptosim.service.PortfolioService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/portfolio")
class PortfolioController(
    private val portfolioService: PortfolioService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun add(@RequestBody profile: PortfolioRequest): PortfolioResponse {
        val email = SecurityContextHolder.getContext().authentication.name
            ?: throw RuntimeException("NO ACCOUNT FOUND")
        return portfolioService.create(profile, email)
    }

    @GetMapping
    fun retrieveAll(@PageableDefault pageable: Pageable) =
        portfolioService.retrieve(pageable)

    @GetMapping("/{id}")
    fun retrieveById(@PathVariable id: Long): PortfolioResponse {
        val email = SecurityContextHolder.getContext().authentication.name
            ?: throw RuntimeException("NO ACCOUNT FOUND")
        return portfolioService.retrieveById(id, email)
    }


    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody profileInput: PortfolioRequest): PortfolioResponse {
        val email = SecurityContextHolder.getContext().authentication.name
            ?: throw RuntimeException("NO ACCOUNT FOUND")
        return portfolioService.update(id, profileInput, email)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Unit? {
        val email = SecurityContextHolder.getContext().authentication.name
            ?: throw RuntimeException("NO ACCOUNT FOUND")
        return portfolioService.delete(id, email)
    }
}