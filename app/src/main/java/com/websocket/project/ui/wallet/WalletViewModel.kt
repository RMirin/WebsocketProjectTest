package com.websocket.project.ui.wallet

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(): ViewModel() {

    val showBalance = ObservableBoolean(true)
}