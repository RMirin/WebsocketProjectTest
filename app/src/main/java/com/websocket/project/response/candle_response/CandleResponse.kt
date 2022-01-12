package com.websocket.project.response.candle_response

import com.google.gson.annotations.SerializedName

data class CandleResponse(
    @SerializedName("ch") val ch: String?,
    @SerializedName("snapshot") val snapshot: HashMap<String, List<Candle>>?,
    @SerializedName("update") val update: HashMap<String, List<Candle>>?
)