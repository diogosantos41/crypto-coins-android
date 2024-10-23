package com.dscoding.cryptocoins.crypto

import android.app.Application
import com.dscoding.cryptocoins.crypto.domain.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoCoinApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoCoinApp)
            androidLogger()

            modules(appModule)
        }
    }
}