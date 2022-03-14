package com.websocket.project.ui.wallet.position_history.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.websocket.project.R
import com.websocket.project.databinding.DialogFragmentPositionHistoryDetailBinding
import com.websocket.project.databinding.FragmentTransactionHistoryBinding
import com.websocket.project.ui.base.BaseDialogFragment
import com.websocket.project.ui.base.convertDpToPixel
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.wallet.position_history.WalletPositionHistoryModel

class DialogFragmentPositionHistoryDetail(
    private val walletPositionHistoryModel: WalletPositionHistoryModel
) : BaseDialogFragment<DialogFragmentPositionHistoryDetailBinding>(), DialogFragmentPositionHistoryDetailListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            listener = this@DialogFragmentPositionHistoryDetail
            positionHistoryModelBinding = walletPositionHistoryModel
        }
    }

    override fun onCloseBtnClick() {
        dismiss()
    }

    override fun initViewBinding(): DialogFragmentPositionHistoryDetailBinding =
        DialogFragmentPositionHistoryDetailBinding.inflate(layoutInflater)
}