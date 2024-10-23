package com.dscoding.cryptocoins.crypto.presentation.coin

import com.dscoding.cryptocoins.crypto.presentation.models.CoinUi

sealed interface CoinAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinAction
}