package com.example.cryptosim.entity

import jakarta.persistence.*
import java.time.LocalDateTime

enum class Role {
    USER, ADMIN
}

@Entity
@Table(name="user_account")
data class UserAccount(
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable=false, unique=true, length=255)
    var email: String,

    @Column(name="password_hash", nullable=false, length=255)
    var passwordHash: String,

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable=false)
    val role: Role
) : Auditor()