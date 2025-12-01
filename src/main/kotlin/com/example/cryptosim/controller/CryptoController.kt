package com.example.cryptosim.controller


import com.example.cryptosim.dto.CryptoAssetResponse
import com.example.cryptosim.dto.LivePriceResponse
import com.example.cryptosim.service.CryptoAssetService
import com.example.cryptosim.service.coincap.AssetSyncService
import com.example.cryptosim.service.coincap.DailyPriceScheduler
import com.example.cryptosim.service.coincap.LivePriceService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/crypto")
class CryptoController(
    private val assetSyncService: AssetSyncService,
    private val livePriceService: LivePriceService,
    private val scheduler: DailyPriceScheduler,
    private val cryptoAssetService: CryptoAssetService
) {
    @GetMapping("/sync-assets")
    fun syncAssets(): String {
        assetSyncService.syncAssets()
        return "Assets synced successfully"
    }

    @GetMapping("/prices/fetch")
    fun fetchPricesNow(): String {
        scheduler.updateDailyPrices()
        return "Prices updated for today."
    }

    @GetMapping("/price/live/{assetId}")
    fun getLivePrice(@PathVariable assetId: String): LivePriceResponse {
        return livePriceService.getLivePrice(assetId)
    }

    @GetMapping("/assets")
    fun getAssets(
        @RequestParam(required=false) symbol: String?,
        @RequestParam(required=false) name: String?,
        @RequestParam(required=false) minPrice: BigDecimal?,
        @RequestParam(required=false) maxPrice: BigDecimal?,
        pageable: Pageable
    ): Page<CryptoAssetResponse> =
        cryptoAssetService.retrieve(symbol, name, minPrice, maxPrice, pageable)

    @GetMapping("/assets/{id}")
    fun getAssetById(@PathVariable id: String): CryptoAssetResponse =
        cryptoAssetService.retrieveById(id)
}