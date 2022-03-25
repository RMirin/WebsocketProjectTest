package com.websocket.project.ui.wallet.sell.partners

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SellPartnersItem (
    @DrawableRes val icon: Int,
    @StringRes val type: Int?,
    @StringRes val namePartners: Int?
)