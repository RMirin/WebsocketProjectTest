package com.websocket.project.ui.base

import android.graphics.Bitmap
import android.text.style.CharacterStyle
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun View.bindIsVisible(isVisible: Boolean) {
    show(isVisible)
}

@BindingAdapter(value = ["show", "isInvisible"], requireAll = false)
fun View.bindVisibility(show: Boolean, isInvisible: Boolean) {
    show(!show, isInvisible)
}

@BindingAdapter("imageBitmap")
fun ImageView.bindImageBitmap(imageBitmap: Bitmap) {
    setImageBitmap(
        imageBitmap
    )
}

@BindingAdapter("imgId")
fun ImageView.bindImgId(imgId: Int) {
    setImageResource(imgId)
}

