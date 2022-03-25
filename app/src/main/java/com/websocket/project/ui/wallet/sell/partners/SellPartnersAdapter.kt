package com.websocket.project.ui.wallet.sell.partners

import android.content.res.Resources
import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemBuyPartnersBinding
import com.websocket.project.databinding.ItemSellPartnersBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding
import com.websocket.project.ui.wallet.buy.BuyFragmentActionListener
import com.websocket.project.ui.wallet.buy.partners.BuyPartnersItem
import com.websocket.project.ui.wallet.sell.SellFragmentActionListener

class SellPartnersAdapter(
    val sellFragmentActionListener: SellFragmentActionListener,
    val localizedResources: Resources?
): BaseRecyclerViewAdapter() {

    private val sellFragmentPartnersItems = mutableListOf<SellPartnersItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BuyPartnersViewHolder(
            parent.inflateWithBinding(
                R.layout.item_sell_partners
            )
        )
    }

    override fun getItemCount() = sellFragmentPartnersItems.size

    fun setPartners(partnersList: MutableList<SellPartnersItem>) {
        sellFragmentPartnersItems.addAll(partnersList)
    }

    private inner class BuyPartnersViewHolder(
        private val itemSellPartnersBinding: ItemSellPartnersBinding
    ) : BaseViewHolder(itemSellPartnersBinding) {
        override fun bind(position: Int) {
            val partnersItem = sellFragmentPartnersItems[position]
            with(itemSellPartnersBinding) {
                sellPartnersItemBinding = partnersItem
                itemSellPartnersImg.setImageResource(partnersItem.icon)
                itemSellPartnersTitleCodeText.text = partnersItem.type?.let {
                    localizedResources?.getString(
                        it
                    )
                }
                itemSellPartnersTitleNameText.text = partnersItem.namePartners?.let {
                    localizedResources?.getString(
                        it
                    )
                }
            }
        }
    }
}