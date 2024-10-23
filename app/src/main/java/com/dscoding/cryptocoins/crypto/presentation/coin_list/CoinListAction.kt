package com.dscoding.cryptocoins.crypto.presentation.coin_list

import com.dscoding.cryptocoins.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
    data object OnRefresh: CoinListAction
}