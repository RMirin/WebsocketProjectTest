package com.websocket.project.ui.candle.fastBuy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.websocket.project.R
import com.websocket.project.databinding.FastBuyBottomSheetLayoutBinding

class FastBuyBottomSheetFragment(
    private val listener: FastBuyBottomSheetListener
) : BottomSheetDialogFragment() {

    lateinit var binding: FastBuyBottomSheetLayoutBinding

    override fun getTheme() = R.style.FastBuyBottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FastBuyBottomSheetLayoutBinding.bind(inflater.inflate(R.layout.fast_buy_bottom_sheet_layout, container))

        return binding.root
    }
}

interface FastBuyBottomSheetListener {
    fun onDepositClick()
    fun onBuyClick()
}