package com.dscoding.cryptocoins.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.cryptocoins.crypto.presentation.coin_list.components.CoinListItem
import com.dscoding.cryptocoins.crypto.presentation.coin_list.components.previewCoin
import com.dscoding.cryptocoins.crypto.presentation.coin_root.CoinRootAction
import com.dscoding.cryptocoins.crypto.presentation.coin_root.CoinRootState
import com.dscoding.cryptocoins.ui.theme.CryptoCoinsTheme


@Composable
fun CoinListScreen(
    state: CoinRootState,
    onAction: (CoinRootAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.coins) { coinUi ->
                CoinListItem(
                    coinUi = coinUi,
                    onClick = { onAction(CoinRootAction.OnCoinClick(coinUi)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
private fun CoinListScreenPreview() {
    CryptoCoinsTheme {
        CoinListScreen(
            state = CoinRootState(coins = (1..100).map {
                previewCoin.copy(id = it.toString())
            }),
            onAction = {},
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}

