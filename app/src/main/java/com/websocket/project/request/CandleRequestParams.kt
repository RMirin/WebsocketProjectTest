package com.websocket.project.request

data class CandleRequestParams(
    val symbols: List<String>,
    val limit: Int
)