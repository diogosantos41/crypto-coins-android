package com.dscoding.cryptocoins.crypto.data.network

import com.dscoding.cryptocoins.core.data.network.constructUrl
import com.dscoding.cryptocoins.core.data.network.safeCall
import com.dscoding.cryptocoins.core.domain.util.NetworkError
import com.dscoding.cryptocoins.core.domain.util.Result
import com.dscoding.cryptocoins.core.domain.util.map
import com.dscoding.cryptocoins.crypto.data.mappers.toCoin
import com.dscoding.cryptocoins.crypto.data.mappers.toCoinPrice
import com.dscoding.cryptocoins.crypto.data.network.dto.CoinHistoryResponse
import com.dscoding.cryptocoins.crypto.data.network.dto.CoinsResponse
import com.dscoding.cryptocoins.crypto.domain.Coin
import com.dscoding.cryptocoins.crypto.domain.CoinDataSource
import com.dscoding.cryptocoins.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class KtorCoinDataSource(private val httpClient: HttpClient) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponse> {
            httpClient.get(urlString = constructUrl("/assets"))
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        val endMillis = end.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        return safeCall<CoinHistoryResponse> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}