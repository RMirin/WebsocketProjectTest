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
import com.websocket.project.ui.base.convertDpToPixel
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.wallet.position_history.WalletPositionHistoryModel

class DialogFragmentPositionHistoryDetail(
    private val walletPositionHistoryModel: WalletPositionHistoryModel
) : DialogFragment(), DialogFragmentPositionHistoryDetailListener {

    lateinit var binding: DialogFragmentPositionHistoryDetailBinding

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        val param: WindowManager.LayoutParams? = dialog?.window?.attributes
        param?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        param?.width = ViewGroup.LayoutParams.MATCH_PARENT
        val back = ColorDrawable(Color.TRANSPARENT)
        val insetFloat = convertDpToPixel(16f, activity as MainActivity)
        val inset = InsetDrawable(back, insetFloat)

        dialog?.window?.setBackgroundDrawable(inset)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogFragmentPositionHistoryDetailBinding.bind(inflater.inflate(R.layout.dialog_fragment_position_history_detail, container))

        with(binding) {
            listener = this@DialogFragmentPositionHistoryDetail
            positionHistoryModelBinding = walletPositionHistoryModel
        }

        return binding.root
    }

    override fun onCloseBtnClick() {
        dismiss()
    }
}