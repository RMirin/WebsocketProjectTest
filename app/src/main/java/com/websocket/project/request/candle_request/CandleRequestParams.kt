package com.websocket.project.request.candle_request

data class CandleRequestParams(
    val symbols: List<String>,
    val limit: Int
)