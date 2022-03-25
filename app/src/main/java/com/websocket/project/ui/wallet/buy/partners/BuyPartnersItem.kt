package com.websocket.project.ui.wallet.buy.partners

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class BuyPartnersItem (
    @DrawableRes val icon: Int,
    @StringRes val type: Int?,
    @StringRes val namePartners: Int?
)