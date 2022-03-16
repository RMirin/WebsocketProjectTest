package com.websocket.project.ui.support.attach_file

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.websocket.project.R
import com.websocket.project.databinding.AttachFileBottomSheetLayoutBinding
import com.websocket.project.databinding.BottomSheetLayoutWithdrawNetworkBinding
import com.websocket.project.ui.base.BaseBottomSheetDialogFragment

class AttachFileBottomSheetFragment(
    private val listener: AttachFileBottomSheetListener
) : BaseBottomSheetDialogFragment<AttachFileBottomSheetLayoutBinding>() {

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AttachFileBottomSheetAdapter(listener)
        with(binding) {
            attachFileBottomSheetLayoutRecycler.adapter = adapter
        }
    }

    override fun initViewBinding(): AttachFileBottomSheetLayoutBinding =
        AttachFileBottomSheetLayoutBinding.inflate(layoutInflater)
}