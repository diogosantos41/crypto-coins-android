package com.dscoding.cryptocoins.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.cryptocoins.core.domain.util.onError
import com.dscoding.cryptocoins.core.domain.util.onSuccess
import com.dscoding.cryptocoins.core.domain.util.toUiText
import com.dscoding.cryptocoins.crypto.domain.CoinDataSource
import com.dscoding.cryptocoins.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(private val coinDataSource: CoinDataSource) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {

            }

            CoinListAction.OnRefresh -> {
                loadCoins()
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
                        CoinListEvent.OnLoadCoinsError(error.toUiText())
                    )
                }
        }
        _state.update { it.copy(isLoading = false) }
    }
}


