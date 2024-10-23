package com.dscoding.cryptocoins.core.data.network

import com.dscoding.cryptocoins.core.domain.util.NetworkError
import com.dscoding.cryptocoins.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        return Result.Error(NetworkError.UNKNOWN)
    }
    return responseToResult(response)
}