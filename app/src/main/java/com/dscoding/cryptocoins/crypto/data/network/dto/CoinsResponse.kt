package com.dscoding.cryptocoins.crypto.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinsResponse(
    val data: List<CoinDto>
)
