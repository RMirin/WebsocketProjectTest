package com.websocket.project.ui.custom.datepicker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DatePickerDay(
    val dayNumber: Int
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        return other is DatePickerDay && other.dayNumber == dayNumber
    }

    override fun hashCode(): Int {
        return dayNumber
    }
}