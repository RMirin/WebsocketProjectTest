package com.websocket.project.ui.transaction_history.detail

interface DialogFragmentTransactionHistoryDetailListener {
    fun onHideAddressClick()
    fun onHideTxHashClick()
    fun onHideTransactionIdClick()
    fun onCloseClick()
    fun onCopyAddressClick()
    fun onCopyTxHashClick()
    fun onCopyTransactionIdClick()
}