package com.websocket.project.ui.wallet.position_history

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemWalletPositionHistoryBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding

class WalletPositionHistoryAdapter(
    private val walletPositionHistoryFragmentListener: WalletPositionHistoryFragmentListener,
    private val walletPositionHistoryList: MutableList<WalletPositionHistoryModel>
) : BaseRecyclerViewAdapter() {

    private var showBalance = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return WalletPositionHistoryViewHolder(parent.inflateWithBinding(R.layout.item_wallet_position_history))
    }

    fun showBalance(show: Boolean) {
        showBalance = show
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = walletPositionHistoryList.size

    private inner class WalletPositionHistoryViewHolder(
        private val itemWalletPositionHistoryBinding: ItemWalletPositionHistoryBinding
    ) : BaseViewHolder(itemWalletPositionHistoryBinding) {
        override fun bind(position: Int) {
            val walletPositionItem = walletPositionHistoryList[position]
            with(itemWalletPositionHistoryBinding) {
                walletPositionHistoryFragmentListenerBinding = walletPositionHistoryFragmentListener
                walletPositionHistoryModelBinding = walletPositionItem
                showBalanceBinding = showBalance
            }
        }
    }
}