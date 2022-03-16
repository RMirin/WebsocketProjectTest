package com.websocket.project.ui.withdraw.network

import androidx.annotation.StringRes
import com.websocket.project.R

enum class WithdrawNetwork(
    @StringRes val networkName: Int,
    @StringRes val networkCode: Int
) {
    ETHEREUM(
        networkName = R.string.network_ethereum_name,
        networkCode = R.string.network_ethereum_code
    ),
    TRON(
        networkName = R.string.network_tron_name,
        networkCode = R.string.network_tron_code
    ),
    BITCOIN(
        networkName = R.string.network_bitcoin_name,
        networkCode = R.string.network_bitcoin_code
    )
}