package com.websocket.project.request

data class SubscribeCandleRequest(
    val method: String,
    val ch: String,
    val params: CandleRequestParams,
    val id: Int
)