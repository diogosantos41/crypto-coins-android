package com.dscoding.cryptocoins.crypto.data.mappers

import com.dscoding.cryptocoins.crypto.data.network.dto.CoinDto
import com.dscoding.cryptocoins.crypto.data.network.dto.CoinPriceDto
import com.dscoding.cryptocoins.crypto.domain.Coin
import com.dscoding.cryptocoins.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        priceUsd = priceUsd,
        marketCapUsd = marketCapUsd,
        changePercent24Hr = changePercent24Hr
    )
}

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant
            .ofEpochMilli(time)
            .atZone(ZoneId.of("UTC"))
    )
}