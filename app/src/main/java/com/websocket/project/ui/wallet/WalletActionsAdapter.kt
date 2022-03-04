package com.websocket.project.ui.wallet

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemWalletActionBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding

class WalletActionsAdapter(
    private val walletActionsListener: WalletActionsListener
) : BaseRecyclerViewAdapter() {

    private val itemsList = WalletAction.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TransactionHistoryViewHolder(parent.inflateWithBinding(R.layout.item_wallet_action))
    }

    private inner class TransactionHistoryViewHolder(
        private val itemWalletActionBinding: ItemWalletActionBinding
    ) : BaseViewHolder(itemWalletActionBinding) {
        override fun bind(position: Int) {
            with(itemWalletActionBinding) {
                val item = itemsList[position]
                this.walletActionListenerBinding = walletActionsListener
                this.walletActionItemBinding = item
            }
        }
    }

    override fun getItemCount(): Int = itemsList.size
}