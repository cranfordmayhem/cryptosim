package com.example.cryptosim.repository.specification

import com.example.cryptosim.dto.AssetFilter
import com.example.cryptosim.entity.CryptoAsset
import com.example.cryptosim.entity.CryptoPriceHistory
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification
import java.math.BigDecimal
import java.time.LocalDate

class CryptoAssetSpecifications {
    companion object {
        fun filter(filter: AssetFilter): Specification<CryptoAsset> {
            return Specification {root, query, cb ->
                val priceJoin = root.join<CryptoAsset,
                        CryptoPriceHistory>("cryptoPriceHistory", JoinType.LEFT)

                val subquery = query!!.subquery(LocalDate::class.java)
                val subRoot = subquery.from(CryptoPriceHistory::class.java)
                subquery.select(cb.greatest(subRoot.get("recordedAt")))
                    .where(cb.equal(subRoot.get<CryptoAsset>("asset"), root))

                val predicates = mutableListOf<Predicate>()

                predicates.add(cb.equal(priceJoin.get<LocalDate>("recordedAt"), subquery))

                filter.symbol?.let {
                    predicates.add(cb.like(cb.lower(root.get("symbol")), "%${it.lowercase()}"))
                }

                filter.name?.let {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%${it.lowercase()}"))
                }

                filter.minPrice?.let {
                    predicates.add(cb.ge(priceJoin.get("priceUsd"), it))
                }

                filter.maxPrice?.let {
                    predicates.add(cb.le(priceJoin.get("priceUsd"), it))
                }

                cb.and(*predicates.toTypedArray())
            }
        }
    }
}