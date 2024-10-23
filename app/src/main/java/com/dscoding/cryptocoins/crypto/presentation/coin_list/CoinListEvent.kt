package com.dscoding.cryptocoins.crypto.presentation.coin_list

import com.dscoding.cryptocoins.core.presentation.util.UiText

interface CoinListEvent {
    data class OnLoadCoinsError(val message: UiText) : CoinListEvent
}