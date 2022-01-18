package com.websocket.project.ui.profile.safety

import com.websocket.project.R

enum class SafetyItem(val nameItem:Int, val withSwitch:Boolean) {
    ENABLE_TOUCH_ID(R.string.enable_touch_id, true),
    TWO_FACTOR_AUTHENTICATION(R.string.two_factor_authentication, false),
    EDIT_PIN_CODE(R.string.edit_pin_code, false),
    ENABLE_PIN_CODE(R.string.enable_pin_code, false),
    CHANGE_PASSWORD(R.string.change_password, false)
}