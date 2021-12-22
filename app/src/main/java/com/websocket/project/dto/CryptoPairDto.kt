package com.websocket.project.dto

import com.google.gson.annotations.SerializedName

data class CryptoPairDto(
    @SerializedName("t") val timestamp: Long?,
    @SerializedName("A") val sequence: String?,
    @SerializedName("a") val asks: String?,
    @SerializedName("b") val bids: String?,
    @SerializedName("P") val priceChangePercent: Double?
)