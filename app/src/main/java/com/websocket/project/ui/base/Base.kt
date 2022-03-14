package com.websocket.project.ui.base

import android.content.Context
import android.graphics.Rect
import android.text.SpannableString
import android.text.Spanned
import android.text.style.CharacterStyle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.facebook.shimmer.ShimmerFrameLayout
import java.math.RoundingMode
import java.text.DecimalFormat


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
//val style: CharacterStyle = ForegroundColorSpan(Color.BLUE)
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

fun roundOffDecimal(number: Double): String {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(number)
}

fun ShimmerFrameLayout.shimmerShow() {
    this.apply {
        startShimmer()
        visibility = View.VISIBLE
    }
}

fun ShimmerFrameLayout.shimmerHide() {
    this.apply {
        stopShimmer()
        visibility = View.GONE
    }
}

class MainActionsRecyclerItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = space

        if (parent.getChildLayoutPosition(view) % 2 == 0) {
            outRect.right = space
        } else {
            outRect.left = space
        }
    }
}

fun View.show(visible: Boolean = false) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

fun View.show(show: Boolean = true, invisible: Boolean = false) {
    visibility = if (show) {
        View.VISIBLE
    } else {
        if (invisible) View.INVISIBLE else View.GONE
    }
}

fun convertDpToPixel(dp: Float, context: Context): Int {
    return (dp * (context.resources
        .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

fun convertPixelsToDp(px: Float, context: Context): Int {
    return (px / (context.resources
        .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}