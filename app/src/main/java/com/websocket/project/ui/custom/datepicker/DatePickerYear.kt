package com.websocket.project.ui.custom.datepicker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DatePickerYear(
    var number: Int
) : Parcelable