package com.websocket.project.ui.transaction_history.folder.withdrawal

import android.os.Build
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import com.websocket.project.R
import com.websocket.project.databinding.ItemTransactionHistoryFolderWithdrawalBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding
import com.websocket.project.ui.transaction_history.TransactionHistoryModel
import com.websocket.project.ui.transaction_history.folder.buy.TransactionHistoryFolderBuyActionListener

class TransactionHistoryFolderWithdrawalAdapter(
    val transactionHistoryFolderWithdrawalActionListener: TransactionHistoryFolderWithdrawalActionListener
) : BaseRecyclerViewAdapter() {

    private val transactionHistoryFolderWithdrawalList = mutableListOf<TransactionHistoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TransactionHistoryFolderWithdrawalViewHolder(parent.inflateWithBinding(R.layout.item_transaction_history_folder_withdrawal))
    }

    fun setData(dataList: List<TransactionHistoryModel>) {
        transactionHistoryFolderWithdrawalList.addAll(dataList)
        notifyItemRangeChanged(0, dataList.size)
    }

    override fun getItemCount(): Int = transactionHistoryFolderWithdrawalList.size

    private inner class TransactionHistoryFolderWithdrawalViewHolder(
        private val itemTransactionHistoryFolderWithdrawalBinding: ItemTransactionHistoryFolderWithdrawalBinding
    ) : BaseViewHolder(itemTransactionHistoryFolderWithdrawalBinding) {
        override fun bind(position: Int) {
            val transactionHistoryItem = transactionHistoryFolderWithdrawalList[position]
            with(itemTransactionHistoryFolderWithdrawalBinding) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    itemTransactionHistoryFolderWithdrawalDateText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                    itemTransactionHistoryFolderWithdrawalAmountText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                    itemTransactionHistoryFolderWithdrawalFeeText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                } else {
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderWithdrawalDateText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderWithdrawalAmountText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderWithdrawalFeeText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                }

                transactionHistoryModelBinding = transactionHistoryItem
            }
        }
    }
}