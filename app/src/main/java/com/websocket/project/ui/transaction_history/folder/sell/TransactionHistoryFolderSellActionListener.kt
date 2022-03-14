package com.websocket.project.ui.transaction_history.folder.sell

import com.websocket.project.ui.transaction_history.TransactionHistoryModel

interface TransactionHistoryFolderSellActionListener {
    fun onTransactionHistoryFolderSellItemClick(transactionHistoryModel: TransactionHistoryModel)
}