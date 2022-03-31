package com.websocket.project.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.websocket.project.R

abstract class BaseBottomSheetDialogFragment<VB : ViewBinding> : BottomSheetDialogFragment() {

    protected lateinit var binding: VB

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initViewBinding().also { viewBinding ->
            this.binding = viewBinding
        }.root
    }

    override fun getTheme() = R.style.BottomSheetDialogTheme

    abstract fun initViewBinding(): VB


}