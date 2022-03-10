package com.websocket.project.ui.wallet.position_history

import com.websocket.project.ui.transaction_history.TransactionStatus
import com.websocket.project.ui.wallet.WalletSideModel

data class WalletPositionHistoryModel(
    var positionId: Long? = 0L,
    val icon: Int?,
    var name: String? = "",
    var nameFull: String? = "",
    var leverage: String? = "",
    var side: WalletSideModel?,
    var status: TransactionStatus?,
    var openingDate: Long? = 0L,
    var openingTime: Long? = 0L,
    var averageSellPrice: Float? = 0f,
    var amountSellCrypto: String? = "",
    var amountSellUsdt: String? = "",
    var closingDate: Long? = 0L,
    var closingTime: Long? = 0L,
    var averageBuyPrice: Float? = 0f,
    var amountBuyCrypto: String? = "",
    var amountBuyUsdt: String? = "",
    var profit: Float? = 0f,
    var fee: Float? = 0f
)