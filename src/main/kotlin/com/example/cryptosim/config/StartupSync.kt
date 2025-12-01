package com.example.cryptosim.config

import com.example.cryptosim.service.coincap.AssetSyncService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class StartupSync(
    private val assetSyncService: AssetSyncService
) {
    @EventListener(ApplicationReadyEvent::class)
    fun init() {
        println("Syncing crypto assets from CoinCap... ")
        assetSyncService.syncAssets()
        println("Asset sync completed.")
    }
}