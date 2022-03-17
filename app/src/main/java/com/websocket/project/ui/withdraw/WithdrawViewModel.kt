package com.websocket.project.ui.withdraw

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.websocket.project.ui.withdraw.network.WithdrawNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(): ViewModel() {

    private val _networkChosenPosition = MutableLiveData(2)
    val networkChosenPosition: LiveData<Int>
        get() = _networkChosenPosition

    private val _transactionFeeTotal = MutableLiveData(0)
    private val availableUsdt = ObservableInt(500)
    val transactionFeeTotal: LiveData<Int>
        get() = _transactionFeeTotal

    var network = MutableStateFlow<String?>(null)

    val transactionFeeNetwork = ObservableInt(50)
    val amountErrorVisible = ObservableBoolean(false)
    val amountCleanVisible = ObservableBoolean(false)

    val addressToSendCleanVisible = ObservableBoolean(false)

    fun onAmountMaxBtnClicked(): Int {
        val amountMaxUsdt = availableUsdt.get() - transactionFeeNetwork.get()
        _transactionFeeTotal.postValue(amountMaxUsdt)
        return amountMaxUsdt
    }

    fun setSelectedNetwork(position: Int) {
        viewModelScope.launch {
            network.emit(WithdrawNetwork.values()[position].name)
        }
        _networkChosenPosition.postValue(position)
    }

    fun setAddress(inputString: String) {
        addressToSendCleanVisible.set(inputString.isNotEmpty())
    }

    fun setAvailableUsdt(inputUsdt: Int) {
        amountCleanVisible.set(inputUsdt > 0)
        if (inputUsdt != 0) {
            _transactionFeeTotal.postValue(inputUsdt + transactionFeeNetwork.get())
        } else {
            _transactionFeeTotal.postValue(0)
        }
        amountErrorVisible.set(availableUsdt.get() < transactionFeeNetwork.get() + inputUsdt)
    }

    fun getAvailableUsdt(): Int {
        return availableUsdt.get()
    }
}