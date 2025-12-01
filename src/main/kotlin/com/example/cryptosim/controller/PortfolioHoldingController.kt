package com.example.cryptosim.controller

import com.example.cryptosim.dto.PortfolioHoldingRequest
import com.example.cryptosim.dto.PortfolioHoldingResponse
import com.example.cryptosim.entity.PortfolioHolding
import com.example.cryptosim.service.PortfolioHoldingService
import org.springframework.data.domain.Page
import org.springframework.hateoas.PagedModel
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/portfolios/{portfolioId}/holdings")
class PortfolioHoldingController(
    private val service: PortfolioHoldingService,
    private val assembler: PagedResourcesAssembler<PortfolioHolding>
) {
    @PostMapping
    fun addHolding(
        @PathVariable portfolioId: Long,
        @RequestBody request: PortfolioHoldingRequest
    ): PortfolioHoldingResponse {
        val email = SecurityContextHolder.getContext().authentication.name
        return service.addHolding(portfolioId, request, email)
    }

    @GetMapping
    fun getHoldings(
        @PathVariable portfolioId: Long,
        pageable: Pageable,
        assembler: PagedResourcesAssembler<PortfolioHoldingResponse>
    ): PagedModel<EntityModel<PortfolioHoldingResponse>> {
        val email = SecurityContextHolder.getContext().authentication.name
        val page = service.getHoldings(portfolioId, pageable, email)
        return assembler.toModel(page) { holdingResponse ->
            EntityModel.of(holdingResponse)
        }
    }
}