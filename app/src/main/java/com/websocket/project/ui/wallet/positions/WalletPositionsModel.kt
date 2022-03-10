package com.websocket.project.ui.wallet.positions

import com.websocket.project.ui.wallet.WalletSideModel

data class WalletPositionsModel(
    var icon: Int?,
    var name: String?,
    var leverage: String?,
    var walletSideModel: WalletSideModel?,
    var totalEx: String?,
    var profit: String?
)