package com.dscoding.cryptocoins.crypto.presentation.coin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.cryptocoins.core.domain.util.onError
import com.dscoding.cryptocoins.core.domain.util.onSuccess
import com.dscoding.cryptocoins.core.domain.util.toUiText
import com.dscoding.cryptocoins.crypto.domain.CoinDataSource
import com.dscoding.cryptocoins.crypto.presentation.models.CoinUi
import com.dscoding.cryptocoins.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class CoinViewModel(private val coinDataSource: CoinDataSource) : ViewModel() {

    private val _state = MutableStateFlow(CoinState())
    val state = _state
        .onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinState()
        )

    private val _events = Channel<CoinEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinAction) {
        when (action) {
            is CoinAction.OnCoinClick -> {
                selectCoin(coinUi = action.coinUi)
            }
        }
    }

    private fun selectCoin(coinUi: CoinUi) {
        _state.update { it.copy(selectionCoin = coinUi) }

        viewModelScope.launch {

            coinDataSource
                .getCoinHistory(
                    coinId = coinUi.id,
                    start = ZonedDateTime.now().minusDays(5),
                    end = ZonedDateTime.now()
                )
                .onSuccess { history ->
                    _state.update {
                        it.copy(
                            selectionCoin = it.selectionCoin?.copy(
                                coinPriceHistory = history
                            )
                        )
                    }
                }
                .onError { error ->
                    _events.send(
                        CoinEvent.OnLoadCoinHistoryError(error.toUiText())
                    )
                }

        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            coinDataSource
                .getCoins()
                .onSuccess { coins ->
                    _state.update { it.copy(coins = coins.map { coin -> coin.toCoinUi() }) }
                }
                .onError { error ->
                    _events.send(
                        CoinEvent.OnLoadCoinsError(error.toUiText())
                    )
                }
            _state.update { it.copy(isLoading = false) }
        }
    }
}


