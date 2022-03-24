package com.websocket.project.ui.wallet.buy

import android.content.res.Resources
import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemBuyPartnersBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding

class BuyPartnersAdapter(
    val buyFragmentActionListener: BuyFragmentActionListener,
    val localizedResources: Resources?
): BaseRecyclerViewAdapter() {

    private val buyFragmentPartnersItems = mutableListOf<BuyPartnersItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BuyPartnersViewHolder(
                parent.inflateWithBinding(
                    R.layout.item_buy_partners
                )
            )
        }

    override fun getItemCount() = buyFragmentPartnersItems.size

    fun setPartners(partnersList: MutableList<BuyPartnersItem>) {
        buyFragmentPartnersItems.addAll(partnersList)
    }

    private inner class BuyPartnersViewHolder(
        private val itemBuyPartnersBinding: ItemBuyPartnersBinding
    ) : BaseViewHolder(itemBuyPartnersBinding) {
        override fun bind(position: Int) {
            val partnersItem = buyFragmentPartnersItems[position]
            with(itemBuyPartnersBinding) {
                buyPartnersItemBinding = partnersItem
                itemBuyPartnersImg.setImageResource(partnersItem.icon)
                itemBuyPartnersTitleCodeText.text = partnersItem.type?.let {
                    localizedResources?.getString(
                        it
                    )
                }
                itemBuyPartnersTitleNameText.text = partnersItem.namePartners?.let {
                    localizedResources?.getString(
                        it
                    )
                }
            }
        }
    }
}