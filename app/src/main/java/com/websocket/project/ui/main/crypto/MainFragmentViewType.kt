package com.websocket.project.ui.main.crypto

import com.google.gson.annotations.SerializedName

enum class MainFragmentViewType(val viewType: Int) {
    @SerializedName("balance")
    BALANCE(0),
    @SerializedName("verification")
    VERIFICATION(1),
    @SerializedName("actions")
    ACTIONS(2),
    @SerializedName("portfolio")
    PORTFOLIO(3),
    @SerializedName("crypto")
    CRYPTO(4)
}