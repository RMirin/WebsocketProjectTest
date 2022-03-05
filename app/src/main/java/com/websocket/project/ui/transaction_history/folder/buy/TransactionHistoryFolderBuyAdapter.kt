package com.websocket.project.ui.transaction_history.folder.buy

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemTransactionHistoryBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding
import com.websocket.project.ui.transaction_history.TransactionHistoryModel

class TransactionHistoryFolderBuyAdapter() : BaseRecyclerViewAdapter() {

    private val transactionHistoryFolderBuyList = mutableListOf<TransactionHistoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TransactionHistoryFolderBuyViewHolder(parent.inflateWithBinding(R.layout.item_transaction_history))
    }

    fun setData(dataList: List<TransactionHistoryModel>) {
        transactionHistoryFolderBuyList.addAll(dataList)
        notifyItemRangeChanged(0, dataList.size)
    }

    override fun getItemCount(): Int = transactionHistoryFolderBuyList.size

    private inner class TransactionHistoryFolderBuyViewHolder(
        private val itemTransactionHistoryBinding: ItemTransactionHistoryBinding
    ) : BaseViewHolder(itemTransactionHistoryBinding) {
        override fun bind(position: Int) {
            val transactionHistoryItem = transactionHistoryFolderBuyList[position]
            with(itemTransactionHistoryBinding) {
                transactionHistoryModelBinding = transactionHistoryItem
            }
        }
    }
}