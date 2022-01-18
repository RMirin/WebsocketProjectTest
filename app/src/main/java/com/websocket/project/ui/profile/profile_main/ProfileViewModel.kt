package com.websocket.project.ui.profile.profile_main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.StringBuilder
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    fun hideEmail(email: String): String {
        return email.replace(
            Regex("(?<=.)[^@\\n](?=[^@\\n]*?@)|(?:(?<=@)|(?!^)\\G(?=[^@\\n]*\$)).(?=.*\\.)"),
            "*"
        )
    }
}