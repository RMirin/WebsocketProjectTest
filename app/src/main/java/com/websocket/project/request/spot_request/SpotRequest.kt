package com.websocket.project.request.spot_request

import com.google.gson.annotations.SerializedName

data class SpotRequest(
    var id: Int = 123,
    var method: String = "spot_subscribe",
    @SerializedName("params")
    var spotRequestParams: SpotRequestParams = SpotRequestParams()
)