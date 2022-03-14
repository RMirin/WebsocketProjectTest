package com.websocket.project.ui.wallet.positions.close

import android.os.Bundle
import android.view.View
import com.websocket.project.databinding.DialogFragmentClosePositionBinding
import com.websocket.project.ui.base.BaseDialogFragment
import com.websocket.project.ui.wallet.positions.WalletPositionsFragmentListener
import com.websocket.project.ui.wallet.positions.WalletPositionsModel

class DialogFragmentClosePosition(
    private val walletPositionsModel: WalletPositionsModel,
    private val walletPositionsFragmentListener: WalletPositionsFragmentListener
): BaseDialogFragment<DialogFragmentClosePositionBinding>(), DialogFragmentClosePositionListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            walletPositionsModelBinding = walletPositionsModel
            dialogFragmentClosePositionListenerBinding = this@DialogFragmentClosePosition
        }
    }

    override fun initViewBinding(): DialogFragmentClosePositionBinding =
        DialogFragmentClosePositionBinding.inflate(layoutInflater)

    override fun onCancelBtnClick() {
        dismiss()
    }

    override fun onCloseBtnClick(walletPositionsModel: WalletPositionsModel) {
        walletPositionsFragmentListener.closePosition(walletPositionsModel)
    }

    companion object {
        const val WALLET_POSITIONS_CLOSE_KEY_DIALOG = "DialogFragmentClosePosition"
    }
}