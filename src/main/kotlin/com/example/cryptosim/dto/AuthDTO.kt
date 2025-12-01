package com.example.cryptosim.dto

import com.example.cryptosim.entity.Role

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val id: Long,
    val username: String,
    val role: Role
)