package com.websocket.project.ui.transaction_history.folder.deposit

import android.os.Build
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import com.websocket.project.R
import com.websocket.project.databinding.ItemTransactionHistoryFolderDepositBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding
import com.websocket.project.ui.transaction_history.TransactionHistoryModel
import com.websocket.project.ui.transaction_history.folder.buy.TransactionHistoryFolderBuyActionListener

class TransactionHistoryFolderDepositAdapter(
    val transactionHistoryFolderDepositActionListener: TransactionHistoryFolderDepositActionListener
) : BaseRecyclerViewAdapter() {

    private val transactionHistoryFolderDepositList = mutableListOf<TransactionHistoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TransactionHistoryFolderDepositViewHolder(parent.inflateWithBinding(R.layout.item_transaction_history_folder_deposit))
    }

    fun setData(dataList: List<TransactionHistoryModel>) {
        transactionHistoryFolderDepositList.addAll(dataList)
        notifyItemRangeChanged(0, dataList.size)
    }

    override fun getItemCount(): Int = transactionHistoryFolderDepositList.size

    private inner class TransactionHistoryFolderDepositViewHolder(
        private val itemTransactionHistoryFolderDepositBinding: ItemTransactionHistoryFolderDepositBinding
    ) : BaseViewHolder(itemTransactionHistoryFolderDepositBinding) {
        override fun bind(position: Int) {
            val transactionHistoryItem = transactionHistoryFolderDepositList[position]
            with(itemTransactionHistoryFolderDepositBinding) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    itemTransactionHistoryFolderDepositDateText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                    itemTransactionHistoryFolderDepositAmountText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                    itemTransactionHistoryFolderDepositFeeText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                } else {
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderDepositDateText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderDepositAmountText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderDepositFeeText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                }

                transactionHistoryModelBinding = transactionHistoryItem
            }
        }
    }
}