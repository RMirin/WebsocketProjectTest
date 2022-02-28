package com.websocket.project.ui.transaction_history

import androidx.annotation.StringRes
import com.websocket.project.R

enum class TransactionHistoryFolder(
    @StringRes val title: Int
) {
    ALL(
        R.string.transaction_history_folder_all
    ),
    DEPOSIT(
        R.string.transaction_history_folder_deposit
    ),
    WITHDRAWAL(
        R.string.transaction_history_folder_withdrawal
    ),
    BUY(
        R.string.transaction_history_folder_buy
    ),
    SELL(
        R.string.transaction_history_folder_sell
    )
}