package com.example.cryptosim.controller

import com.example.cryptosim.dto.*
import com.example.cryptosim.service.*
import org.springframework.web.bind.annotation.*
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated

@Validated
@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthenticationService, private val userAccService: UserAccountService) {

    @PostMapping("/login")
    fun login(
        @RequestBody authRequest: LoginRequest,
        response: HttpServletResponse
    ): LoginResponse =
        authService.authenticate(authRequest, response)

    @GetMapping("/logout")
    fun logout(response: HttpServletResponse) = authService.logout(response)

    @PostMapping("/refresh")
    fun refreshAccessToken(
        @RequestBody request: RefreshTokenRequest,
        response: HttpServletResponse
    ): TokenResponse =
        TokenResponse(accessToken = authService.refreshAccessToken(request.refreshToken, response))

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    fun register(@Valid @RequestBody user: UserAccountRequest) =
        userAccService.register(user)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody user: UserAccountRequest) =
        userAccService.update(id, user)
}