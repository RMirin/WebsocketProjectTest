package com.websocket.project.request.ticker_request

data class SubscribeTickerRequest(
    val method: String,
    val ch: String,
    val params: TickerRequestParams,
    val id: Int
)