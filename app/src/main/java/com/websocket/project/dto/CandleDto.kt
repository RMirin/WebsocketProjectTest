package com.websocket.project.dto

import com.google.gson.annotations.SerializedName

data class CandleDto(
    @SerializedName("c")
    val close: Float,
    @SerializedName("h")
    val high: Float,
    @SerializedName("l")
    val low: Float,
    @SerializedName("o")
    val open: Float,
    @SerializedName("q")
    val quoteAssetVolume: String,
    @SerializedName("t")
    val timestamp: Long,
    @SerializedName("v")
    val baseAssetVolume: String
)