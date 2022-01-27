package com.websocket.project.dto

import com.google.gson.annotations.SerializedName

data class CandleDto(
    @SerializedName("t") val timestamp: Long?,
    @SerializedName("o") val open: String?,
    @SerializedName("c") val close: String?,
    @SerializedName("h") val high: String?,
    @SerializedName("l") val low: String?,
    @SerializedName("v") val baseAssetVolume: String?,
    @SerializedName("q") val quoteAssetVolume: String?
)