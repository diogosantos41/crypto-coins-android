package com.dscoding.cryptocoins

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dscoding.cryptocoins.crypto.presentation.coin_root.CoinNavigation
import com.dscoding.cryptocoins.ui.theme.CryptoCoinsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoCoinsTheme {
                    CoinNavigation()
                }
            }
        }
    }

