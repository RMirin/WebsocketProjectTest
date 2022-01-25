package com.websocket.project.ui.main

import androidx.lifecycle.ViewModel
import com.tinder.scarlet.Scarlet
import com.websocket.project.usecases.WebSocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val webSocketUseCase: WebSocketUseCase) :
    ViewModel() {
}