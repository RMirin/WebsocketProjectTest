package com.websocket.project.response.spot_response

data class SpotResponseParam(
    val client_order_id: String,
    val created_at: String,
    val id: Long,
    val post_only: Boolean,
    val price: String,
    val quantity: String,
    val quantity_cumulative: String,
    val report_type: String,
    val side: String,
    val status: String,
    val symbol: String,
    val time_in_force: String,
    val type: String,
    val updated_at: String
)