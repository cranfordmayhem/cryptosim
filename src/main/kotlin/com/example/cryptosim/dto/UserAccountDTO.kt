package com.example.cryptosim.dto

import com.example.cryptosim.entity.Role

data class UserAccountRequest(
    val email: String,
    val password: String,
    val role: Role = Role.USER
)

data class UserAccountResponse(
    val id: Long?,
    val email: String,
    val role: Role
)