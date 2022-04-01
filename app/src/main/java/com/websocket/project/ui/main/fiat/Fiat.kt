package com.websocket.project.ui.main.fiat

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.websocket.project.R

data class Fiat(
    @DrawableRes
    val icon: Int,
    @StringRes
    val titleCode: Int,
    @StringRes
    val titleName: Int,
    var isChecked: Boolean
) {
    override fun equals(other: Any?): Boolean {
        return this.titleCode == (other as Fiat).titleCode
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}