package com.websocket.project.request

data class SubscribeTickerRequest(
    val method: String,
    val ch: String,
    val params: TickerRequestParams,
    val id: Int
)