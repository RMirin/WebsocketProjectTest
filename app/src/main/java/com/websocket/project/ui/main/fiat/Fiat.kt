package com.websocket.project.ui.main.fiat

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.websocket.project.R

enum class Fiat(
    @DrawableRes
    val icon: Int,
    @StringRes
    val titleCode: Int,
    @StringRes
    val titleName: Int,
    var isChecked: Boolean
) {
    ARS(
        icon = R.drawable.ic_fiat_peso,
        titleCode = R.string.fiat_code_ars,
        titleName = R.string.fiat_name_ars,
        isChecked = false
    ),
    BYN(
        icon = R.drawable.ic_fial_byn,
        titleCode = R.string.fiat_code_byn,
        titleName = R.string.fiat_name_byn,
        isChecked = false
    ),
    CLP(
        icon = R.drawable.ic_fiat_peso,
        titleCode = R.string.fiat_code_clp,
        titleName = R.string.fiat_name_clp,
        isChecked = false
    ),
    COP(
        icon = R.drawable.ic_fiat_peso,
        titleCode = R.string.fiat_code_cop,
        titleName = R.string.fiat_name_cop,
        isChecked = false
    ),
    EUR(
        icon = R.drawable.ic_fiat_eur,
        titleCode = R.string.fiat_code_eur,
        titleName = R.string.fiat_name_eur,
        isChecked = false
    ),
    IDR(
        icon = R.drawable.ic_fiat_idr,
        titleCode = R.string.fiat_code_idr,
        titleName = R.string.fiat_name_idr,
        isChecked = false
    ),
    INR(
        icon = R.drawable.ic_fiat_inr,
        titleCode = R.string.fiat_code_inr,
        titleName = R.string.fiat_name_inr,
        isChecked = false
    ),
    KZT(
        icon = R.drawable.ic_fiat_kzt,
        titleCode = R.string.fiat_code_kzt,
        titleName = R.string.fiat_name_kzt,
        isChecked = false
    ),
    MXN(
        icon = R.drawable.ic_fiat_peso,
        titleCode = R.string.fiat_code_mxn,
        titleName = R.string.fiat_name_mxn,
        isChecked = false
    ),
    MYR(
        icon = R.drawable.ic_fiat_myr,
        titleCode = R.string.fiat_code_myr,
        titleName = R.string.fiat_name_myr,
        isChecked = false
    ),
    PEN(
        icon = R.drawable.ic_fiat_pen,
        titleCode = R.string.fiat_code_pen,
        titleName = R.string.fiat_name_pen,
        isChecked = false
    ),
    PHP(
        icon = R.drawable.ic_fiat_peso,
        titleCode = R.string.fiat_code_php,
        titleName = R.string.fiat_name_php,
        isChecked = false
    ),
    RUB(
        icon = R.drawable.ic_fiat_rub,
        titleCode = R.string.fiat_code_rub,
        titleName = R.string.fiat_name_rub,
        isChecked = false
    ),
    THB(
        icon = R.drawable.ic_fiat_thb,
        titleCode = R.string.fiat_code_thb,
        titleName = R.string.fiat_name_thb,
        isChecked = false
    ),
    UAH(
        icon = R.drawable.ic_fiat_uah,
        titleCode = R.string.fiat_code_uah,
        titleName = R.string.fiat_name_uah,
        isChecked = false
    ),
    USD(
        icon = R.drawable.ic_fiat_usd,
        titleCode = R.string.fiat_code_usd,
        titleName = R.string.fiat_name_usd,
        isChecked = false
    ),
    VEF(
        icon = R.drawable.ic_fiat_vef,
        titleCode = R.string.fiat_code_vef,
        titleName = R.string.fiat_name_vef,
        isChecked = false
    ),
    VND(
        icon = R.drawable.ic_fiat_vnd,
        titleCode = R.string.fiat_code_vnd,
        titleName = R.string.fiat_name_vnd,
        isChecked = false
    )
}