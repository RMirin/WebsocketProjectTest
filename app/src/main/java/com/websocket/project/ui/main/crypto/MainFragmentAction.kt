package com.websocket.project.ui.main.crypto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.websocket.project.R

enum class MainFragmentAction(
    @DrawableRes val icon: Int,
    @StringRes val title: Int
) {
    DEPOSIT(
        R.drawable.ic_main_action_deposit,
        R.string.main_action_deposit
    ),
    BUY(
        R.drawable.ic_main_action_buy,
        R.string.main_action_buy
    ),
    WITHDRAW(
        R.drawable.ic_main_action_withdraw,
        R.string.main_action_withdraw
    ),
    SELL(
        R.drawable.ic_main_action_sell,
        R.string.main_action_sell
    )
}