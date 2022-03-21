package com.websocket.project.ui.deposit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.websocket.project.ui.network.Network
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DepositViewModel @Inject constructor(): ViewModel() {

    private val _networkChosenPosition = MutableLiveData(-1)
    val networkChosenPosition: LiveData<Int>
        get() = _networkChosenPosition

    private val _depositFee = MutableLiveData(0)
    val depositFee: LiveData<Int>
        get() = _depositFee

    var network = MutableStateFlow<String?>(null)

    fun setSelectedNetwork(position: Int) {
        viewModelScope.launch {
            network.emit(Network.values()[position].name)
        }
        _networkChosenPosition.postValue(position)
    }
}