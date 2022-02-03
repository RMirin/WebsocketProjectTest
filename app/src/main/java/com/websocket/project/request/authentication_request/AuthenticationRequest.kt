package com.websocket.project.request.authentication_request

import com.google.gson.annotations.SerializedName

data class AuthenticationRequest(
    var method: String = "login",
    @SerializedName("params")
    var authenticationRequestParams: AuthenticationRequestParams = AuthenticationRequestParams()
)