package com.dscoding.cryptocoins.crypto.presentation.coin_root

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.cryptocoins.core.presentation.util.ObserveAsEvents
import com.dscoding.cryptocoins.crypto.presentation.coin_detail.CoinDetailScreen
import com.dscoding.cryptocoins.crypto.presentation.coin_list.CoinListScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoinNavigation(viewModel: CoinRootViewModel = koinViewModel<CoinRootViewModel>()) {
    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is CoinRootEvent.OnLoadCoinsError -> {
                Toast.makeText(
                    context,
                    event.message.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val onAction = viewModel::onAction

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        when {
            state.selectionCoin != null -> {
                CoinDetailScreen(
                    state = state,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            else -> {
                CoinListScreen(
                    state = state,
                    onAction = onAction,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}