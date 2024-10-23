package com.dscoding.cryptocoins.crypto.presentation.coin

import com.dscoding.cryptocoins.core.presentation.util.UiText

interface CoinEvent {
    data class OnLoadCoinsError(val message: UiText) : CoinEvent
    data class OnLoadCoinHistoryError(val message: UiText) : CoinEvent
}