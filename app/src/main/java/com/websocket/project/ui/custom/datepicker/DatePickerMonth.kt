package com.websocket.project.ui.custom.datepicker

import androidx.annotation.StringRes
import com.websocket.project.R

enum class DatePickerMonth(
    val number: Int,
    @StringRes val monthName: Int
) {
    JANUARY(
        number = 1,
        monthName = R.string.month_january
    ),
    FEBRUARY(
        number = 2,
        monthName = R.string.month_february
    ),
    MARCH(
        number = 3,
        monthName = R.string.month_march
    ),
    APRIL(
        number = 4,
        monthName = R.string.month_april
    ),
    MAY(
        number = 5,
        monthName = R.string.month_may
    ),
    JUNE(
        number = 6,
        monthName = R.string.month_june
    ),
    JULY(
        number = 7,
        monthName = R.string.month_july
    ),
    AUGUST(
        number = 8,
        monthName = R.string.month_august
    ),
    SEPTEMBER(
        number = 9,
        monthName = R.string.month_september
    ),
    OCTOBER(
        number = 10,
        monthName = R.string.month_october
    ),
    NOVEMBER(
        number = 11,
        monthName = R.string.month_november
    ),
    DECEMBER(
        number = 12,
        monthName = R.string.month_december
    )
}