package com.websocket.project.ui.network

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemNetworkBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding
import java.util.ArrayList

class NetworkAdapter(
    val networkList: Array<Network>,
    val networkBottomSheetActionListener: NetworkBottomSheetActionListener
): BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return WithdrawNetworkItemViewHolder(parent.inflateWithBinding(R.layout.item_network))
    }

    override fun getItemCount() = networkList.size

    private inner class WithdrawNetworkItemViewHolder(private val itemNetworkBinding: ItemNetworkBinding) : BaseViewHolder(itemNetworkBinding) {
        override fun bind(position: Int) {

            val withdrawNetworkItem = networkList[position]
            with(itemNetworkBinding) {
                val context = this.root.context
                val name = context.getString(withdrawNetworkItem.networkName)
                val code = context.getString(withdrawNetworkItem.networkCode)
                networkItemBinding = withdrawNetworkItem
                networkItemText = context.getString(R.string.withdraw_network_btn, name, code)
                networkBottomSheetActionListenerBinding = networkBottomSheetActionListener
            }
        }
    }
}