package com.websocket.project.ui.alert

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.websocket.project.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class AlertType(
    @DrawableRes val iconId: Int?,
    @StringRes val titleId: Int,
    @StringRes val msgId: Int?
): Parcelable {
    TECHNICAL_WORKS(
        iconId = R.drawable.ic_alert_techincal_works,
        titleId = R.string.alert_technical_works_title,
        msgId = R.string.alert_technical_works_msg
    ),
    MISSING_CONNECTION_WIFI(
        iconId = R.drawable.ic_alert_missing_connection_wifi,
        titleId = R.string.alert_missing_connection_wifi_title,
        msgId = R.string.alert_missing_connection_wifi_msg
    ),
    MISSING_CONNECTION_MOBILE(
        iconId = R.drawable.ic_alert_missing_connection_mobile,
        titleId = R.string.alert_missing_connection_mobile_title,
        msgId = R.string.alert_missing_connection_mobile_msg
    ),
    IMAGE_SAVED(
        iconId = null,
        titleId = R.string.alert_image_saved_title,
        msgId = null
    )
}