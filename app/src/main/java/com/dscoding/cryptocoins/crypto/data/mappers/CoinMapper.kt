package com.dscoding.cryptocoins.crypto.data.mappers

import com.dscoding.cryptocoins.crypto.data.network.dto.CoinDto
import com.dscoding.cryptocoins.crypto.domain.Coin

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