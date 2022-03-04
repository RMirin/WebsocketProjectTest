package com.websocket.project.ui.wallet

data class WalletPositionHistoryModel(
    var icon: Int?,
    var name: String?,
    var nameFull: String?,
    var leverage: String?,
    var date: Long?,
    var time: Long?,
    var profit: String?, // String ?
    var walletSideModel: WalletSideModel?
)