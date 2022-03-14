package com.websocket.project.ui.transaction_history.folder.buy

import android.os.Build
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import com.websocket.project.R
import com.websocket.project.databinding.ItemTransactionHistoryFolderBuyBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding
import com.websocket.project.ui.transaction_history.TransactionHistoryModel
import com.websocket.project.ui.transaction_history.folder.all.TransactionHistoryFolderAllActionListener

class TransactionHistoryFolderBuyAdapter(
    val transactionHistoryFolderBuyActionListener: TransactionHistoryFolderBuyActionListener
) : BaseRecyclerViewAdapter() {

    private val transactionHistoryFolderBuyList = mutableListOf<TransactionHistoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TransactionHistoryFolderBuyViewHolder(parent.inflateWithBinding(R.layout.item_transaction_history_folder_buy))
    }

    fun setData(dataList: List<TransactionHistoryModel>) {
        transactionHistoryFolderBuyList.addAll(dataList)
        notifyItemRangeChanged(0, dataList.size)
    }

    override fun getItemCount(): Int = transactionHistoryFolderBuyList.size

    private inner class TransactionHistoryFolderBuyViewHolder(
        private val itemTransactionHistoryFolderBuyBinding: ItemTransactionHistoryFolderBuyBinding
    ) : BaseViewHolder(itemTransactionHistoryFolderBuyBinding) {
        override fun bind(position: Int) {
            val transactionHistoryItem = transactionHistoryFolderBuyList[position]
            with(itemTransactionHistoryFolderBuyBinding) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    itemTransactionHistoryFolderBuyDateText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                    itemTransactionHistoryFolderBuyAmountText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                    itemTransactionHistoryFolderBuyFeeText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                } else {
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderBuyDateText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderBuyAmountText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderBuyFeeText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                }

                transactionHistoryModelBinding = transactionHistoryItem
            }
        }
    }
}