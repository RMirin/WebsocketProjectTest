package com.websocket.project.ui.main.attach_file

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.websocket.project.R
import com.websocket.project.databinding.AttachFileBottomSheetLayoutBinding

class AttachFileBottomSheetFragment(
    private val listener: AttachFileBottomSheetListener
) : BottomSheetDialogFragment() {

    lateinit var binding: AttachFileBottomSheetLayoutBinding

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AttachFileBottomSheetLayoutBinding.bind(inflater.inflate(R.layout.attach_file_bottom_sheet_layout, container))

        val adapter = AttachFileBottomSheetAdapter(listener)
        adapter.setFilterActions(AttachFileAction.values())
        binding.bottomSheetLayoutRecycler.adapter = adapter

        return binding.root
    }
}

interface AttachFileBottomSheetListener {
    fun onAttachFileClick(action: AttachFileAction)
}