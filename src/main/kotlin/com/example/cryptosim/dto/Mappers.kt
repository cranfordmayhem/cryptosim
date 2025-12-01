package com.example.cryptosim.dto

import com.example.cryptosim.entity.*
import java.math.BigDecimal
import java.time.Instant

fun RefreshTokenRequest.toEntity(userAccount: UserAccount, expiryDate: Instant): RefreshToken = RefreshToken(
    token = this.refreshToken,
    expiryDate = expiryDate,
    userAccount = userAccount
)

fun UserAccountRequest.toEntity(): UserAccount = UserAccount(
    email = this.email,
    passwordHash = this.password,
    role = this.role
)

fun UserAccount.toResponse(): UserAccountResponse = UserAccountResponse(
    id = this.id,
    email = this.email,
    role = this.role
)

fun UserProfileRequest.toEntity(userAccount: UserAccount): UserProfile = UserProfile(
    firstName = this.firstName,
    lastName = this.lastName,
    user = userAccount,
    age = this.age
)

fun UserProfile.toResponse(): UserProfileResponse = UserProfileResponse(
    id = this.id,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.user.email,
    age = this.age
)

fun CryptoAsset.toResponse(): CryptoAssetResponse {
    val latestPrice = cryptoPriceHistory.maxByOrNull{ it.recordedAt }
        ?: throw  RuntimeException("No price for asset $id")

    return CryptoAssetResponse(
        id = id,
        symbol = symbol,
        name = name,
        priceUsd = latestPrice.priceUsd,
        recordedAt = latestPrice.recordedAt
    )
}

fun PortfolioRequest.toEntity(userAccount: UserAccount): Portfolio = Portfolio(
    name = this.name ?: "Main Portfolio",
    user = userAccount
)

fun Portfolio.toResponse(): PortfolioResponse = PortfolioResponse(
    id = this.id,
    name = this.name
)

fun PortfolioHoldingRequest.toEntity(portfolio: Portfolio, asset: CryptoAsset): PortfolioHolding = PortfolioHolding(
    portfolio = portfolio,
    asset = asset,
    amount = this.amount
)

fun PortfolioHolding.toResponse(): PortfolioHoldingResponse = PortfolioHoldingResponse(
    id = this.id,
    assetId = this.asset.id,
    amount = this.amount
)

fun PortfolioHolding.toPricedResponse(price: BigDecimal, totalValue: BigDecimal): PricedHoldingResponse = PricedHoldingResponse(
    id = this.id,
    assetId = this.asset.id,
    amount = this.amount,
    price = price,
    totalValue = totalValue
)