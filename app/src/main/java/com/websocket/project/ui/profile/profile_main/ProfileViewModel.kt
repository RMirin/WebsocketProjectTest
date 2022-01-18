package com.websocket.project.ui.profile.profile_main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.StringBuilder
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    //bad decision sorry, later I will rewrite normally
    fun hideEmail(email: String): String {
        val stringBuilder = StringBuilder()
        var splitEmail = email.split("@")
        stringBuilder.append(email[0])
        for (i in 1..splitEmail[0].length) {
            stringBuilder.append("*")
        }
        stringBuilder.append("@")
        splitEmail = splitEmail[1].split(".")
        splitEmail[0].forEach { _ ->
            stringBuilder.append("*")
        }
        stringBuilder.append(".").append(splitEmail[1])

        return stringBuilder.toString()
    }
}