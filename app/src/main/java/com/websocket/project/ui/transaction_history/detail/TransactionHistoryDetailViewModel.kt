package com.websocket.project.ui.transaction_history.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionHistoryDetailViewModel @Inject constructor(
): ViewModel() {

    private val _addressShown = MutableLiveData(true)
    val addressShown: LiveData<Boolean>
        get() = _addressShown

    private val _txHashShown = MutableLiveData(true)
    val txHashShown: LiveData<Boolean>
        get() = _txHashShown

    private val _transactionIdShown = MutableLiveData(true)
    val transactionIdShown: LiveData<Boolean>
        get() = _transactionIdShown

    fun showHideAddress() {
        _addressShown.postValue(!(_addressShown.value?:true))
    }

    fun showHideTxHash() {
        _txHashShown.postValue(!(_txHashShown.value?:true))
    }

    fun showHideTransactionId() {
        _transactionIdShown.postValue(!(_transactionIdShown.value?:true))
    }
}