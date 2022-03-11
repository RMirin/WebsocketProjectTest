package com.websocket.project.ui.transaction_history.folder.deposit

import com.websocket.project.ui.transaction_history.TransactionHistoryModel

interface TransactionHistoryFolderDepositActionListener {
    fun onTransactionHistoryFolderDepositItemClick(transactionHistoryModel: TransactionHistoryModel)
}