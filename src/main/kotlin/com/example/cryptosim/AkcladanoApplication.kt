package com.example.cryptosim

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "springAuditorAware")
class AkcladanoApplication

fun main(args: Array<String>) {
	runApplication<AkcladanoApplication>(*args)
}
