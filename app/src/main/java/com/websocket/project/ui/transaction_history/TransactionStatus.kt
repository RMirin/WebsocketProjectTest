package com.websocket.project.ui.transaction_history

import androidx.annotation.StringRes
import com.websocket.project.R

enum class TransactionStatus(
    @StringRes val statusText: Int
) {
    CLOSED(
        statusText = R.string.transaction_status_closed
    )
}