package com.websocket.project.ui.withdraw.network

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemWithdrawNetworkBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding

class WithdrawNetworkAdapter(
    val withdrawNetworkBottomSheetActionListener: WithdrawNetworkBottomSheetActionListener
): BaseRecyclerViewAdapter() {

    private val withdrawNetworkList = WithdrawNetwork.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return WithdrawNetworkItemViewHolder(parent.inflateWithBinding(R.layout.item_withdraw_network))
    }

    override fun getItemCount() = withdrawNetworkList.size

    private inner class WithdrawNetworkItemViewHolder(private val itemWithdrawNetworkBinding: ItemWithdrawNetworkBinding) : BaseViewHolder(itemWithdrawNetworkBinding) {
        override fun bind(position: Int) {

            val withdrawNetworkItem = withdrawNetworkList[position]
            with(itemWithdrawNetworkBinding) {
                val context = this.root.context
                val name = context.getString(withdrawNetworkItem.networkName)
                val code = context.getString(withdrawNetworkItem.networkCode)
                withdrawNetworkItemBinding = withdrawNetworkItem
                withdrawNetworkItemText = context.getString(R.string.withdraw_network_name, name, code)
                withdrawNetworkBottomSheetActionListenerBinding = withdrawNetworkBottomSheetActionListener
            }
        }
    }
}