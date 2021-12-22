package com.websocket.project.response

import com.google.gson.annotations.SerializedName
import com.websocket.project.dto.CryptoPairDto

data class CryptoResponse(
    @SerializedName("ch") val ch: String?,
    @SerializedName("data") val cryptoResponseData: Map<String, CryptoPairDto>
)