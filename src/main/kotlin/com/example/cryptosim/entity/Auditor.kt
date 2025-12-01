package com.example.cryptosim.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class Auditor {

    @Column(name="created_date", updatable = false)
    @CreatedDate
    var createdAt: Instant? = null

    @Column(name="created_by",updatable = false)
    @CreatedBy
    var createdBy: String? = null

    @Column(name="last_modified_date")
    @LastModifiedDate
    var updatedAt: Instant? = null

    @Column(name="last_modified_by")
    @LastModifiedBy
    var updatedBy: String? = null
}