package com.dscoding.cryptocoins.core.domain.util

import android.content.Context
import com.dscoding.cryptocoins.R
import com.dscoding.cryptocoins.core.presentation.util.UiText

fun NetworkError.toUiText(): UiText {
    return when (this) {
        NetworkError.REQUEST_TIMEOUT -> UiText.StringResource(R.string.error_request_timeout)
        NetworkError.TOO_MANY_REQUESTS -> UiText.StringResource(R.string.error_too_many_requests)
        NetworkError.NO_INTERNET -> UiText.StringResource(R.string.error_internet_connection)
        else -> UiText.StringResource(R.string.error_unknown)
    }
}