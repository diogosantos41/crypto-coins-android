package com.dscoding.cryptocoins.crypto.presentation.coin_root

import com.dscoding.cryptocoins.crypto.presentation.models.CoinUi

sealed interface CoinRootAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinRootAction
    data object OnRefresh: CoinRootAction
}