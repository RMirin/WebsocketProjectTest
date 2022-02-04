package com.websocket.project.util

import android.text.Layout
import android.view.ViewTreeObserver
import android.widget.TextView

fun TextView.setMaxVisibleLine(){
    val vto: ViewTreeObserver = this.viewTreeObserver
    vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            val obs: ViewTreeObserver = this@setMaxVisibleLine.viewTreeObserver
            obs.removeOnGlobalLayoutListener(this)
            val height = this@setMaxVisibleLine.height
            val scrollY = this@setMaxVisibleLine.scrollY
            val layout: Layout = this@setMaxVisibleLine.layout
            this@setMaxVisibleLine.maxLines = layout.getLineForVertical(height + scrollY)
        }
    })
}