package com.websocket.project.ui.wallet

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.websocket.project.R

enum class WalletAction(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    DEPOSIT(
        title = R.string.wallet_action_deposit,
        icon = R.drawable.ic_wallet_action_deposit
    ),

    BUY(
        title = R.string.wallet_action_buy,
        icon = R.drawable.ic_wallet_action_buy
    ),

    WITHDRAW(
        title = R.string.wallet_action_withdraw,
        icon = R.drawable.ic_wallet_action_withdraw
    ),

    SELL(
        title = R.string.sell,
        icon = R.drawable.ic_wallet_action_sell
    )
}