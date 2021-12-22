package com.websocket.project.ui.base

import android.text.SpannableString
import android.text.Spanned
import android.text.style.CharacterStyle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) {
    liveData.observe(this, Observer(body))
}

inline fun <reified T : ViewDataBinding> ViewGroup.inflateWithBinding(
    layoutId: Int,
    attachToRoot: Boolean = false
): T {
    val inflater = LayoutInflater.from(context)

    return inflater.inflateBinding(layoutId, this, attachToRoot)
}

inline fun <reified T : ViewDataBinding> LayoutInflater.inflateBinding(
    layoutId: Int,
    viewGroup: ViewGroup?,
    attachToRoot: Boolean = false
): T = DataBindingUtil.inflate(this, layoutId, viewGroup, attachToRoot)

inline fun <S : CharacterStyle> TextView.spanAll(characterStyle: S, stringToSpan: String) {
    if (stringToSpan.isEmpty()) return

    var startSpanIndex = text.indexOf(stringToSpan).takeUnless { it == -1 } ?: return
    var endSpanIndex = startSpanIndex + stringToSpan.length

    do {
        span(
            characterStyle,
            startSpanIndex, endSpanIndex
        )
        startSpanIndex = text.indexOf(stringToSpan, endSpanIndex)
        endSpanIndex = startSpanIndex + stringToSpan.length
    } while (startSpanIndex > 0)
}

inline fun <S : CharacterStyle> TextView.span(
    span: S,
    start: Int = 0, end: Int = text.length,
    flags: Int = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
) {
    if (text.isEmpty()) return

    val spannedString = asSpannableString.apply {
        setSpan(
            span,
            start, end,
            flags
        )
    }

    setText(spannedString, TextView.BufferType.SPANNABLE)
}

val TextView.asSpannableString: SpannableString
    get() = SpannableString(text)