package com.websocket.project.ui.support

import android.graphics.Bitmap
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
import com.websocket.project.databinding.DialogSupportFragmentImageDetailBinding
import com.websocket.project.ui.base.convertDpToPixel
import com.websocket.project.ui.main.MainActivity

class DialogFragmentSupportImageDetail(
    private val bitmapToShow: Bitmap
) : DialogFragment() {

    lateinit var binding: DialogSupportFragmentImageDetailBinding

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        val param: WindowManager.LayoutParams? = dialog?.window?.attributes
        param?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        param?.width = ViewGroup.LayoutParams.MATCH_PARENT
        val back = ColorDrawable(Color.TRANSPARENT)
        val insetFloat = convertDpToPixel(41f, activity as MainActivity)
        val inset = InsetDrawable(back, insetFloat)

        dialog?.window?.setBackgroundDrawable(inset)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogSupportFragmentImageDetailBinding.bind(inflater.inflate(R.layout.dialog_support_fragment_image_detail, container))

        with(binding) {
            imageBitmap = bitmapToShow
            supportFragmentImageDetailLayout.setOnClickListener {
                dismiss()
            }
        }

        return binding.root
    }
}