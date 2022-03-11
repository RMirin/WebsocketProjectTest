package com.websocket.project.ui.transaction_history.folder.sell

import android.os.Build
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import com.websocket.project.R
import com.websocket.project.databinding.ItemTransactionHistoryFolderSellBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding
import com.websocket.project.ui.transaction_history.TransactionHistoryModel
import com.websocket.project.ui.transaction_history.folder.buy.TransactionHistoryFolderBuyActionListener

class TransactionHistoryFolderSellAdapter(
    val transactionHistoryFolderSellActionListener: TransactionHistoryFolderSellActionListener
) : BaseRecyclerViewAdapter() {

    private val transactionHistoryFolderSellList = mutableListOf<TransactionHistoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TransactionHistoryFolderSellViewHolder(parent.inflateWithBinding(R.layout.item_transaction_history_folder_sell))
    }

    fun setData(dataList: List<TransactionHistoryModel>) {
        transactionHistoryFolderSellList.addAll(dataList)
        notifyItemRangeChanged(0, dataList.size)
    }

    override fun getItemCount(): Int = transactionHistoryFolderSellList.size

    private inner class TransactionHistoryFolderSellViewHolder(
        private val itemTransactionHistoryFolderSellBinding: ItemTransactionHistoryFolderSellBinding
    ) : BaseViewHolder(itemTransactionHistoryFolderSellBinding) {
        override fun bind(position: Int) {
            val transactionHistoryItem = transactionHistoryFolderSellList[position]
            with(itemTransactionHistoryFolderSellBinding) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    itemTransactionHistoryFolderSellDateText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                    itemTransactionHistoryFolderSellAmountText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                    itemTransactionHistoryFolderSellFeeText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                } else {
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderSellDateText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderSellAmountText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderSellFeeText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                }

                transactionHistoryModelBinding = transactionHistoryItem
            }
        }
    }
}