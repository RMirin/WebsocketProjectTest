package com.websocket.project.ui.transaction_history

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemMainCryptoBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding

class TransactionHistoryAdapter(
    private val transactionHistoryList: MutableList<TransactionHistoryModel>
) : BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TransactionHistoryViewHolder(parent.inflateWithBinding(R.layout.item_transaction_history))
    }

    override fun getItemCount(): Int = transactionHistoryList.size

    private inner class TransactionHistoryViewHolder(
        private val itemMainCryptoBinding: ItemMainCryptoBinding
    ) : BaseViewHolder(itemMainCryptoBinding) {
        override fun bind(position: Int) {
            val transactionHistoryItem = transactionHistoryList[position]
        }
    }
}