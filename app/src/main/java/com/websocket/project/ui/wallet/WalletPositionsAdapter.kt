package com.websocket.project.ui.wallet

import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableBoolean
import com.websocket.project.R
import com.websocket.project.databinding.ItemWalletPositionHistoryBinding
import com.websocket.project.databinding.ItemWalletPositionsBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding

class WalletPositionsAdapter(
    private val walletPositionsList: MutableList<WalletPositionsModel>
) : BaseRecyclerViewAdapter() {

    private var showBalance = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return WalletPositionsViewHolder(parent.inflateWithBinding(R.layout.item_wallet_positions))
    }

    override fun getItemCount(): Int = walletPositionsList.size

    fun showBalance(show: Boolean) {
        showBalance = show
        notifyDataSetChanged()
    }

    private inner class WalletPositionsViewHolder(
        private val itemWalletPositionsBinding: ItemWalletPositionsBinding
    ) : BaseViewHolder(itemWalletPositionsBinding) {
        override fun bind(position: Int) {
            val walletPositionItem = walletPositionsList[position]
            with(itemWalletPositionsBinding) {
                showBalanceBinding = showBalance
                nameBinding = walletPositionItem.name
                leverageBinding = walletPositionItem.leverage
                imageBinding = walletPositionItem.icon
            }
        }
    }
}