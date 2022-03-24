package com.websocket.project.ui.wallet.buy

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.websocket.project.R

data class BuyPartnersItem (
    @DrawableRes val icon: Int,
    @StringRes val type: Int?,
    @StringRes val namePartners: Int?
)