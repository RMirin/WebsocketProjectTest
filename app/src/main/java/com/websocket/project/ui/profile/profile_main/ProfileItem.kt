package com.websocket.project.ui.profile.profile_main

import com.websocket.project.R

enum class ProfileItem(val itemIcon: Int, val itemName: Int) {
    SETTINGS(R.drawable.ic_setting, R.string.settings),
    SAFETY(R.drawable.ic_lock, R.string.safety),
    NOTIFICATION(R.drawable.ic_bell, R.string.notification),
    SUPPORT(R.drawable.ic_chat, R.string.support)
}