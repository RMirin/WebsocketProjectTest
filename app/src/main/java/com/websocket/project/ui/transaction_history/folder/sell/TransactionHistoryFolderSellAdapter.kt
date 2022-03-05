package com.websocket.project.ui.transaction_history.folder.sell

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemTransactionHistoryBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding
import com.websocket.project.ui.transaction_history.TransactionHistoryModel

class TransactionHistoryFolderSellAdapter() : BaseRecyclerViewAdapter() {

    private val transactionHistoryFolderSellList = mutableListOf<TransactionHistoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TransactionHistoryFolderSellViewHolder(parent.inflateWithBinding(R.layout.item_transaction_history))
    }

    fun setData(dataList: List<TransactionHistoryModel>) {
        transactionHistoryFolderSellList.addAll(dataList)
        notifyItemRangeChanged(0, dataList.size)
    }

    override fun getItemCount(): Int = transactionHistoryFolderSellList.size

    private inner class TransactionHistoryFolderSellViewHolder(
        private val itemTransactionHistoryBinding: ItemTransactionHistoryBinding
    ) : BaseViewHolder(itemTransactionHistoryBinding) {
        override fun bind(position: Int) {
            val transactionHistoryItem = transactionHistoryFolderSellList[position]
            with(itemTransactionHistoryBinding) {
                transactionHistoryModelBinding = transactionHistoryItem
            }
        }
    }
}