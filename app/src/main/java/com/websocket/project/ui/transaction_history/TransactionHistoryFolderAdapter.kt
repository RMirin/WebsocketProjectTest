package com.websocket.project.ui.transaction_history

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemTransactionHistoryFolderBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding

class TransactionHistoryFolderAdapter(
    private val transactionHistoryFolderListener: TransactionHistoryFolderListener,
    initCheckedValue: Int
) : BaseRecyclerViewAdapter() {

    private var initValue = initCheckedValue
    private val itemsList = TransactionHistoryFolder.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TransactionHistoryViewHolder(parent.inflateWithBinding(R.layout.item_transaction_history_folder))
    }

    private inner class TransactionHistoryViewHolder(
        private val itemTransactionHistoryFolderBinding: ItemTransactionHistoryFolderBinding
    ) : BaseViewHolder(itemTransactionHistoryFolderBinding) {
        override fun bind(position: Int) {
            with(itemTransactionHistoryFolderBinding) {
                val item = itemsList[position]

                transactionHistoryFolderText.isChecked = (initValue == position)
                transactionHistoryFolderText.text =
                    transactionHistoryFolderText.context.getText(item.title)
                transactionHistoryFolderText.setOnClickListener {
                    transactionHistoryFolderListener.onTransactionHistoryFolderSelected(item)
                }
            }
        }
    }

    fun onTransactionHistoryFolderSelected(position: Int) {
        initValue = position
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itemsList.size
}