package com.websocket.project.ui.network

import android.os.Parcelable
import androidx.annotation.StringRes
import com.websocket.project.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Network(
    @StringRes val networkName: Int,
    @StringRes val networkCode: Int
) : Parcelable {
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