package com.websocket.project.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.websocket.project.ui.main.MainActivity

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

    protected lateinit var binding: VB

    final override fun onStart() {
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

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initViewBinding().also { viewBinding ->
            this.binding = viewBinding
        }.root
    }

    abstract fun initViewBinding(): VB

}