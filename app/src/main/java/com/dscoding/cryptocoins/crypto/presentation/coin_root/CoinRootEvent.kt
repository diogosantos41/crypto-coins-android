package com.dscoding.cryptocoins.crypto.presentation.coin_root

import com.dscoding.cryptocoins.core.presentation.util.UiText

interface CoinRootEvent {
    data class OnLoadCoinsError(val message: UiText) : CoinRootEvent
}