@file:OptIn(ExperimentalLayoutApi::class)

package com.dscoding.cryptocoins.crypto.presentation.coin_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dscoding.cryptocoins.R
import com.dscoding.cryptocoins.crypto.presentation.coin.CoinState
import com.dscoding.cryptocoins.crypto.presentation.coin_detail.components.CoinPriceChart
import com.dscoding.cryptocoins.crypto.presentation.coin_detail.components.InfoCard
import com.dscoding.cryptocoins.crypto.presentation.coin_list.components.previewCoin
import com.dscoding.cryptocoins.crypto.presentation.models.toDisplayableNumber
import com.dscoding.cryptocoins.ui.theme.CryptoCoinsTheme
import com.dscoding.cryptocoins.ui.theme.greenBackground

@Composable
fun CoinDetailScreen(
    state: CoinState,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.selectionCoin != null) {
        val coin = state.selectionCoin
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = coin.iconRes),
                contentDescription = coin.name,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = coin.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                color = contentColor
            )
            Text(
                text = coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = contentColor
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                InfoCard(
                    title = stringResource(id = R.string.market_cap),
                    formattedText = "$ ${coin.marketCapUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.stock)
                )
                InfoCard(
                    title = stringResource(id = R.string.price),
                    formattedText = "$ ${coin.priceUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.dollar)
                )
                val absoluteChangeFormatted =
                    (coin.priceUsd.value * (coin.changePercent24Hr.value) / 100).toDisplayableNumber()
                val isPositive = coin.changePercent24Hr.value > 0.0
                val contentColor = if (isPositive) {
                    if (isSystemInDarkTheme()) Color.Green else greenBackground
                } else {
                    MaterialTheme.colorScheme.error
                }
                InfoCard(
                    title = stringResource(id = R.string.change_last_24hr),
                    formattedText = absoluteChangeFormatted.formatted,
                    icon = if (isPositive) {
                        ImageVector.vectorResource(R.drawable.trending)
                    } else {
                        ImageVector.vectorResource(R.drawable.trending_down)
                    },
                    contentColor = contentColor
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(
                visible = coin.coinPriceHistory.isNotEmpty()
            ) {
                CoinPriceChart(
                    prices = coin.coinPriceHistory,
                    style = LineChartStyle(
                        lineColor = MaterialTheme.colorScheme.primary.toArgb(),
                        lineWidth = 2f,
                        valueColor = MaterialTheme.colorScheme.onSurface.toArgb(),
                        gridColor = MaterialTheme.colorScheme.onSurface.toArgb(),
                        gridLineWidth = 1f
                    )
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview() {
    CryptoCoinsTheme {
        CoinDetailScreen(
            state = CoinState(selectionCoin = previewCoin),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}