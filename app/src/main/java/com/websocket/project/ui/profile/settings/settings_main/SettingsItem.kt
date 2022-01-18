package com.websocket.project.ui.profile.settings.settings_main

import com.websocket.project.R

enum class SettingsItem(val nameItem: Int, val withFlag: Boolean) {
    LANGUAGE(R.string.language, true),
    NOTIFICATION(R.string.notification, false)
}