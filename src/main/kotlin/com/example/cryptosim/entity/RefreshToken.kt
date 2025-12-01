package com.example.cryptosim.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name="refresh_tokens")
data class RefreshToken(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable=false, unique=true)
    val token: String,

    @Column(nullable=false)
    val expiryDate: Instant,

    @Column(nullable=false)
    var used: Boolean = false,

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_account_id", nullable=false)
    val userAccount: UserAccount
) : Auditor()