package com.websocket.project.ui.base

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.net.Uri
import android.provider.OpenableColumns
import android.text.SpannableString
import android.text.Spanned
import android.text.style.CharacterStyle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import java.math.RoundingMode
import java.text.CharacterIterator
import java.text.DecimalFormat
import java.text.StringCharacterIterator
import java.util.*
import kotlin.math.roundToInt


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

fun roundOffDecimal(number: Double): String {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(number)
}

fun getScaledBitmap(picturePath: String, width: Int, height: Int): Bitmap? {
    val sizeOptions = BitmapFactory.Options()
    sizeOptions.inJustDecodeBounds = true
    BitmapFactory.decodeFile(picturePath, sizeOptions)
    val inSampleSize = calculateInSampleSize(sizeOptions, width, height)
    sizeOptions.inJustDecodeBounds = false
    sizeOptions.inSampleSize = inSampleSize
    return BitmapFactory.decodeFile(picturePath, sizeOptions)
}

private fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int,
    reqHeight: Int
): Int {
    // Raw height and width of image
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {

        // Calculate ratios of height and width to requested height and
        // width
        val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
        val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()

        // Choose the smallest ratio as inSampleSize value, this will
        // guarantee
        // a final image with both dimensions larger than or equal to the
        // requested height and width.
        inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
    }
    return inSampleSize
}

fun humanReadableByteCountSI(bytesToConvert: Int): String? {
    var bytes = bytesToConvert
    if (-1000 < bytes && bytes < 1000) {
        return "$bytes B"
    }
    val ci: CharacterIterator = StringCharacterIterator("kMGTPE")
    while (bytes <= -999950 || bytes >= 999950) {
        bytes /= 1000
        ci.next()
    }
    return java.lang.String.format(Locale.US, "%.1f %cB", bytes / 1000.0, ci.current())
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

fun displayName(uri: Uri): String {
    var filename = ""
    val mCursor: Cursor? =
        getApplicationContext<Context>().contentResolver.query(uri, null, null, null, null)
    if (mCursor != null) {
        val indexedname: Int = mCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        mCursor.moveToFirst()
        filename = mCursor.getString(indexedname)
        mCursor.close()
    }
    return filename
}

@NonNull
fun getLocalizedResources(context: Context, desiredLocale: Locale?): Resources? {
    var conf: Configuration = context.resources.configuration
    conf = Configuration(conf)
    conf.setLocale(desiredLocale)
    val localizedContext: Context = context.createConfigurationContext(conf)
    return localizedContext.resources
}

fun getBitmapFromView(view: View): Bitmap? {
    val bitmap =
        Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun View.accessibleTouchTarget() {
    post {
        val delegateArea = Rect()
        getHitRect(delegateArea)

        // 48 dp is the minimum requirement. We need to convert this to pixels.
        val accessibilityMin = convertDpToPixel(48f, context)

        // Calculate size vertically, and adjust touch area if it's smaller then the minimum.
        val height = delegateArea.bottom - delegateArea.top
        if (accessibilityMin > height) {
            // Add +1 px just in case min - height is odd and will be rounded down
            val addition = ((accessibilityMin - height) / 2).toInt() + 1
            delegateArea.top -= addition
            delegateArea.bottom += addition
        }

        // Calculate size horizontally, and adjust touch area if it's smaller then the minimum.
        val width = delegateArea.right - delegateArea.left
        if (accessibilityMin > width) {
            // Add +1 px just in case min - width is odd and will be rounded down
            val addition = ((accessibilityMin - width) / 2).toInt() + 1
            delegateArea.left -= addition
            delegateArea.right += addition
        }

        val parentView = parent as? View
        parentView?.touchDelegate = TouchDelegate(delegateArea, this)
    }
}

class BaseRecyclerItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space
        }
    }
}
