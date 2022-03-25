package com.websocket.project.ui.wallet.sell

import com.websocket.project.ui.main.fiat.Fiat
import com.websocket.project.ui.wallet.buy.partners.BuyPartnersItem
import com.websocket.project.ui.wallet.sell.partners.SellPartnersItem

interface SellFragmentActionListener {
    fun onFiatItemClick(fiat: Fiat)
    fun onFiatBtnClick()
    fun onPartnersItemClick(partnersItem: SellPartnersItem)
}