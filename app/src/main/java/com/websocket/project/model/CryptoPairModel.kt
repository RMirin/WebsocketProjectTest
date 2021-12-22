package com.websocket.project.model

data class CryptoPairModel(
    var name: String?,
    var timestamp: Long?,
    var sequence: String?,
    var asks: String?,
    var bids: String?,
    var priceChangePercent: Double
)