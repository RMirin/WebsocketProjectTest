package com.websocket.project.ui.wallet

import androidx.annotation.StringRes
import com.websocket.project.R

enum class WalletSideModel(
    @StringRes val sideName: Int
) {
    LONG(sideName = R.string.side_long),
    SHORT(sideName = R.string.side_short)
}