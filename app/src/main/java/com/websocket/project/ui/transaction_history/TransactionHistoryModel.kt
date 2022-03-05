package com.websocket.project.ui.transaction_history

data class TransactionHistoryModel(
    var name: String?,
    var nameFull: String?,
    var date: Long,
    var time: Long,
    var type: TransactionType?,
    var status: TransactionStatus?,
    var amount: Float,
    var fee: String
)