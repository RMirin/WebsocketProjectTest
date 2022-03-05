package com.websocket.project.ui.transaction_history.folder.all

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemTransactionHistoryBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding
import com.websocket.project.ui.transaction_history.TransactionHistoryModel

class TransactionHistoryFolderAllAdapter : BaseRecyclerViewAdapter() {

    private val transactionHistoryFolderAllList = mutableListOf<TransactionHistoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TransactionHistoryFolderAllViewHolder(parent.inflateWithBinding(R.layout.item_transaction_history))
    }

    fun setData(dataList: List<TransactionHistoryModel>) {
        transactionHistoryFolderAllList.addAll(dataList)
        notifyItemRangeChanged(0, dataList.size)
    }

    override fun getItemCount(): Int = transactionHistoryFolderAllList.size

    private inner class TransactionHistoryFolderAllViewHolder(
        private val itemTransactionHistoryBinding: ItemTransactionHistoryBinding
    ) : BaseViewHolder(itemTransactionHistoryBinding) {
        override fun bind(position: Int) {
            val transactionHistoryItem = transactionHistoryFolderAllList[position]
            with(itemTransactionHistoryBinding) {
                transactionHistoryModelBinding = transactionHistoryItem
            }
        }
    }
}