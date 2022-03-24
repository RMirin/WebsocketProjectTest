package com.websocket.project.ui.wallet.buy

import com.websocket.project.ui.main.fiat.Fiat

interface BuyFragmentActionListener {
    fun onFiatItemClick(fiat: Fiat)
    fun onFiatBtnClick()
    fun onPartnersItemClick(partnersItem: BuyPartnersItem)
}