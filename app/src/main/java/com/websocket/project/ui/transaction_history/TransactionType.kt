package com.websocket.project.ui.transaction_history

import androidx.annotation.StringRes
import com.websocket.project.R

enum class TransactionType(
    @StringRes val typeText: Int
) {
    SELL(
        typeText = R.string.transaction_history_type_sell
    ),
    WITHDRAWAL(
        typeText = R.string.transaction_history_type_withdrawal
    ),
    BUY(
        typeText = R.string.transaction_history_type_buy
    ),
    DEPOSIT(
        typeText = R.string.transaction_history_type_deposit
    )
}