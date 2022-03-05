package com.websocket.project.ui.transaction_history.folder.deposit

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemTransactionHistoryBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding
import com.websocket.project.ui.transaction_history.TransactionHistoryModel

class TransactionHistoryFolderDepositAdapter() : BaseRecyclerViewAdapter() {

    private val transactionHistoryFolderDepositList = mutableListOf<TransactionHistoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TransactionHistoryFolderDepositViewHolder(parent.inflateWithBinding(R.layout.item_transaction_history))
    }

    fun setData(dataList: List<TransactionHistoryModel>) {
        transactionHistoryFolderDepositList.addAll(dataList)
        notifyItemRangeChanged(0, dataList.size)
    }

    override fun getItemCount(): Int = transactionHistoryFolderDepositList.size

    private inner class TransactionHistoryFolderDepositViewHolder(
        private val itemTransactionHistoryBinding: ItemTransactionHistoryBinding
    ) : BaseViewHolder(itemTransactionHistoryBinding) {
        override fun bind(position: Int) {
            val transactionHistoryItem = transactionHistoryFolderDepositList[position]
            with(itemTransactionHistoryBinding) {
                transactionHistoryModelBinding = transactionHistoryItem
            }
        }
    }
}