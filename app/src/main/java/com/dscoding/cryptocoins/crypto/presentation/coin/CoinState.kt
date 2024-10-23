package com.dscoding.cryptocoins.crypto.presentation.coin

import androidx.compose.runtime.Immutable
import com.dscoding.cryptocoins.crypto.presentation.models.CoinUi

@Immutable
data class CoinState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectionCoin: CoinUi? = null
)
