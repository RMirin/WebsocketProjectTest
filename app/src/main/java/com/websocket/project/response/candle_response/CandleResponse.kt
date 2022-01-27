package com.websocket.project.response.candle_response

import com.google.gson.annotations.SerializedName
import com.websocket.project.dto.CandleDto

data class CandleResponse(
    @SerializedName("ch") val ch: String?,
    @SerializedName("snapshot") val snapshot: HashMap<String, List<CandleDto>>?,
    @SerializedName("update") val update: HashMap<String, List<CandleDto>>?
)

//class Snapshot {
//    @JsonProperty("BTCUSDT")
//    var bTCUSDT: ArrayList<BTCUSDT>? = null
//}
//
//class Root {
//    var ch: String? = null
//    var snapshot: Snapshot? = null
//}