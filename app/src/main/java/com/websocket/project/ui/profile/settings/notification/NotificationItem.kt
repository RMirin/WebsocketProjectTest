package com.websocket.project.ui.profile.settings.notification

import com.websocket.project.R

enum class NotificationItem(val nameItem: Int, val withSwitch:Boolean) {
    APP_NOTIFICATION(R.string.app_notification, false),
    NOTIFICATION(R.string.notification, true),
    EMAIL_NOTIFICATION(R.string.email_notification, false),
    MARKETING_COMPANIES(R.string.marketing_companies, true)
}