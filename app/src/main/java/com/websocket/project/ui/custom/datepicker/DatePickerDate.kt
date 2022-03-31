package com.websocket.project.ui.custom.datepicker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DatePickerDate(
    val chosenDay: DatePickerDay,
    val chosenMonth: DatePickerMonth,
    val chosenYear: DatePickerYear
) : Parcelable