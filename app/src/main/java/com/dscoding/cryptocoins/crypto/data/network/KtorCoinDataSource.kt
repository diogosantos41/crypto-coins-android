package com.dscoding.cryptocoins.crypto.data.network

import com.dscoding.cryptocoins.core.data.network.constructUrl
import com.dscoding.cryptocoins.core.data.network.safeCall
import com.dscoding.cryptocoins.core.domain.util.NetworkError
import com.dscoding.cryptocoins.core.domain.util.Result
import com.dscoding.cryptocoins.core.domain.util.map
import com.dscoding.cryptocoins.crypto.data.mappers.toCoin
import com.dscoding.cryptocoins.crypto.data.network.dto.CoinResponse
import com.dscoding.cryptocoins.crypto.domain.Coin
import com.dscoding.cryptocoins.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorCoinDataSource(private val httpClient: HttpClient) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinResponse> {
            httpClient.get(urlString = constructUrl("/assets"))
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }
}