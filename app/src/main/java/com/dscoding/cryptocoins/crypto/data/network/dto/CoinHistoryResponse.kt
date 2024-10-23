package com.dscoding.cryptocoins.crypto.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryResponse(
    val data: List<CoinPriceDto>
)
