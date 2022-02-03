package com.websocket.project.response.spot_response

data class SpotResponse(
    val jsonrpc: String,
    val method: String,
    val spotResponseParams: List<SpotResponseParam>
)