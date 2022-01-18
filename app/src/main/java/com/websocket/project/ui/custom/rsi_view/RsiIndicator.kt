package com.websocket.project.ui.custom.rsi_view

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.websocket.project.R

enum class RsiIndicator(
    @StringRes val rsiText: Int,
    @ColorRes val rsiColor: Int
) {
    ACTIVELY_SELL(
        rsiText = R.string.actively_sell,
        rsiColor = R.color.actively_sell
    ),
    SELL(
        rsiText = R.string.sell,
        rsiColor = R.color.sell
    ),
    NEUTRAL(
        rsiText = R.string.neutral,
        rsiColor = R.color.neutral
    ),
    BUY(
        rsiText = R.string.buy,
        rsiColor = R.color.buy
    ),
    ACTIVELY_BUY(
        rsiText = R.string.actively_buy,
        rsiColor = R.color.actively_buy
    )
}