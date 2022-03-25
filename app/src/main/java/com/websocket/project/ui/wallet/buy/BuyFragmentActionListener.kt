package com.websocket.project.ui.wallet.buy

import com.websocket.project.ui.main.fiat.Fiat
import com.websocket.project.ui.wallet.buy.partners.BuyPartnersItem

interface BuyFragmentActionListener {
    fun onFiatItemClick(fiat: Fiat)
    fun onFiatBtnClick()
    fun onPartnersItemClick(partnersItem: BuyPartnersItem)
}