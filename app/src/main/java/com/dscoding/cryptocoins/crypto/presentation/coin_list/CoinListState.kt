package com.dscoding.cryptocoins.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.dscoding.cryptocoins.crypto.presentation.models.CoinUi

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectionCoin: CoinUi? = null
)
