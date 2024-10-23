package com.dscoding.cryptocoins.crypto.domain

import com.dscoding.cryptocoins.core.data.network.HttpClientFactory
import com.dscoding.cryptocoins.crypto.data.network.KtorCoinDataSource
import com.dscoding.cryptocoins.crypto.presentation.coin_root.CoinRootViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create())}
    singleOf(::KtorCoinDataSource).bind<CoinDataSource>()
    viewModelOf(::CoinRootViewModel)
}