package com.dscoding.cryptocoins.crypto.domain

import com.dscoding.cryptocoins.core.domain.util.NetworkError
import com.dscoding.cryptocoins.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}