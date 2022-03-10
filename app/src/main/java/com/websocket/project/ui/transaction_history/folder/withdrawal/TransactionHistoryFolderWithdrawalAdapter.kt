package com.websocket.project.ui.transaction_history.folder.withdrawal

import android.os.Build
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import com.websocket.project.R
import com.websocket.project.databinding.ItemTransactionHistoryBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding
import com.websocket.project.ui.transaction_history.TransactionHistoryModel

class TransactionHistoryFolderWithdrawalAdapter() : BaseRecyclerViewAdapter() {

    private val transactionHistoryFolderWithdrawalList = mutableListOf<TransactionHistoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TransactionHistoryFolderWithdrawalViewHolder(parent.inflateWithBinding(R.layout.item_transaction_history))
    }

    fun setData(dataList: List<TransactionHistoryModel>) {
        transactionHistoryFolderWithdrawalList.addAll(dataList)
        notifyItemRangeChanged(0, dataList.size)
    }

    override fun getItemCount(): Int = transactionHistoryFolderWithdrawalList.size

    private inner class TransactionHistoryFolderWithdrawalViewHolder(
        private val itemTransactionHistoryBinding: ItemTransactionHistoryBinding
    ) : BaseViewHolder(itemTransactionHistoryBinding) {
        override fun bind(position: Int) {
            val transactionHistoryItem = transactionHistoryFolderWithdrawalList[position]
            with(itemTransactionHistoryBinding) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    itemTransactionHistoryDateText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                    itemTransactionHistoryAmountText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                    itemTransactionHistoryFeeText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                } else {
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryDateText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryAmountText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFeeText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                }

                transactionHistoryModelBinding = transactionHistoryItem
            }
        }
    }
}