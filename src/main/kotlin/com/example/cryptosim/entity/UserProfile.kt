package com.example.cryptosim.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name="user_profile")
data class UserProfile(
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false, unique=true)
    val user: UserAccount,

    @Column(name="first_name", nullable=false)
    val firstName: String,

    @Column(name="last_name", nullable=false)
    val lastName: String,

    @Column(nullable=false)
    val age: Int
) : Auditor()