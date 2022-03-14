package com.websocket.project.ui.base

import android.graphics.Bitmap
import android.graphics.drawable.ClipDrawable
import android.text.style.CharacterStyle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.websocket.project.R

@BindingAdapter("isVisible")
fun View.bindIsVisible(isVisible: Boolean) {
    show(isVisible)
}

@BindingAdapter(value = ["show", "isInvisible"], requireAll = false)
fun View.bindVisibility(show: Boolean, isInvisible: Boolean) {
    show(!show, isInvisible)
}

@BindingAdapter("isErrorText")
fun View.bindIsErrorText(isErrorText: Boolean) {
    background = if (isErrorText) {
        ResourcesCompat.getDrawable(resources, R.drawable.bg_rectangle_gradient_error_view, null)
    } else {
        ResourcesCompat.getDrawable(resources, R.drawable.bg_rectangle_gradient_view, null)
    }
}

@BindingAdapter("imageBitmap")
fun ImageView.bindImageBitmap(imageBitmap: Bitmap) {
    setImageBitmap(
        imageBitmap
    )
}