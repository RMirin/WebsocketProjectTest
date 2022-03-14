package com.websocket.project.ui.wallet.positions.close

import com.websocket.project.ui.wallet.positions.WalletPositionsModel

interface DialogFragmentClosePositionListener {
    fun onCancelBtnClick()
    fun onCloseBtnClick(walletPositionsModel: WalletPositionsModel)
}