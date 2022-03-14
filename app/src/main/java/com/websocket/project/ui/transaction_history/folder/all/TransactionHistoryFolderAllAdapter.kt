package com.websocket.project.ui.transaction_history.folder.all

import android.os.Build
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import com.websocket.project.R
import com.websocket.project.databinding.ItemTransactionHistoryFolderAllBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding
import com.websocket.project.ui.transaction_history.TransactionHistoryModel

class TransactionHistoryFolderAllAdapter(
    val transactionHistoryFolderAllActionListener: TransactionHistoryFolderAllActionListener
) : BaseRecyclerViewAdapter() {

    private val transactionHistoryFolderAllModelList = mutableListOf<TransactionHistoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TransactionHistoryFolderAllViewHolder(parent.inflateWithBinding(R.layout.item_transaction_history_folder_all))
    }

    fun setData(dataList: List<TransactionHistoryModel>) {
        transactionHistoryFolderAllModelList.addAll(dataList)
        notifyItemRangeChanged(0, dataList.size)
    }

    override fun getItemCount(): Int = transactionHistoryFolderAllModelList.size

    private inner class TransactionHistoryFolderAllViewHolder(
        private val itemTransactionHistoryFolderAllBinding: ItemTransactionHistoryFolderAllBinding
    ) : BaseViewHolder(itemTransactionHistoryFolderAllBinding) {
        override fun bind(position: Int) {
            val transactionHistoryItem = transactionHistoryFolderAllModelList[position]
            with(itemTransactionHistoryFolderAllBinding) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    itemTransactionHistoryFolderAllDateText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                    itemTransactionHistoryFolderAllAmountText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                    itemTransactionHistoryFolderAllFeeText.setAutoSizeTextTypeUniformWithConfiguration(
                        12, 16, 2, TypedValue.COMPLEX_UNIT_DIP)
                } else {
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderAllDateText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderAllAmountText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(itemTransactionHistoryFolderAllFeeText, 12, 16, 1,
                        TypedValue.COMPLEX_UNIT_DIP)
                }

                transactionHistoryModelBinding = transactionHistoryItem
                transactionHistoryFolderAllActionListenerBinding = transactionHistoryFolderAllActionListener
            }
        }
    }
}