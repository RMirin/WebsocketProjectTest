package com.websocket.project.ui.transaction_history.folder.withdrawal

import com.websocket.project.ui.transaction_history.TransactionHistoryModel

interface TransactionHistoryFolderWithdrawalActionListener {
    fun onTransactionHistoryFolderWithdrawalItemClick(transactionHistoryModel: TransactionHistoryModel)
}