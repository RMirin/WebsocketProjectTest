package com.websocket.project.ui.base

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.gson.internal.bind.util.ISO8601Utils.format
import java.lang.String.format
import android.text.format.DateFormat.format
import androidx.core.content.ContextCompat
import com.websocket.project.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter(value = ["show", "isInvisible"], requireAll = false)
fun View.bindVisibility(show: Boolean, isInvisible: Boolean) {
    show(show, isInvisible)
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

@BindingAdapter("date")
fun TextView.bindDate(dateTime: Long) {
    val date = Date(dateTime)
    val format = SimpleDateFormat("dd MMM yyyy", Locale.US)
    val dateText = format.format(date).replace(".","")
    text = dateText
}

@BindingAdapter("time")
fun TextView.bindTime(dateTime: Long) {
    val date = Date(dateTime)
    val format = SimpleDateFormat("HH:mm", Locale.US)
    text = format.format(date)
}

@BindingAdapter("priceColored")
fun TextView.bindPriceColored(price: Float) {
    if (price < 0f) {
        setTextColor(ContextCompat.getColor(context, R.color.common_price_down))
    } else {
        setTextColor(ContextCompat.getColor(context, R.color.common_price_up))
    }
    text = context.getString(R.string.main_price_format_with_currency, price.toString())
}

@BindingAdapter(value = ["price", "canBeText"], requireAll = false)
fun TextView.bindPriceIsText(price: Float, canBeText: Boolean) {
    text = if (canBeText) {
        context.getString(R.string.common_no_fee)
    } else {
        context.getString(R.string.main_price_format_with_currency, price.toString())
    }
}

