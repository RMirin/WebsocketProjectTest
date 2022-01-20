package com.websocket.project.ui.custom.rsi_view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.TextViewCompat
import com.websocket.project.R

class RsiView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var rsiViewActivelySellText: TextView
    private lateinit var rsiViewSellText: TextView
    private lateinit var rsiViewNeutralText: TextView
    private lateinit var rsiViewBuyText: TextView
    private lateinit var rsiViewActivelyBuyText: TextView
    private lateinit var rsiViewSeekBar: AppCompatSeekBar
    private var rsiSeekBarThumb: Drawable? = null

    init {
        initView()
    }

    private fun initView() {
        View.inflate(context, R.layout.view_rsi, this)

        try {
            rsiViewActivelySellText = findViewById(R.id.layout_rsi_actively_sell_text)
            rsiViewSellText = findViewById(R.id.layout_rsi_sell_text)
            rsiViewNeutralText = findViewById(R.id.layout_rsi_neutral_text)
            rsiViewBuyText = findViewById(R.id.layout_rsi_buy_text)
            rsiViewActivelyBuyText = findViewById(R.id.layout_rsi_actively_buy_text)

            rsiViewSeekBar = findViewById(R.id.rsi_view_seekbar)
            rsiViewSeekBar.isEnabled = false

            rsiSeekBarThumb = ContextCompat.getDrawable(context, R.drawable.ic_rsi_arrow)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                rsiViewActivelySellText.setAutoSizeTextTypeUniformWithConfiguration(
                    12, 16, 2, TypedValue.COMPLEX_UNIT_DIP
                )
                rsiViewSellText.setAutoSizeTextTypeUniformWithConfiguration(
                    12, 16, 2, TypedValue.COMPLEX_UNIT_DIP
                )
                rsiViewNeutralText.setAutoSizeTextTypeUniformWithConfiguration(
                    12, 16, 2, TypedValue.COMPLEX_UNIT_DIP
                )
                rsiViewBuyText.setAutoSizeTextTypeUniformWithConfiguration(
                    12, 16, 2, TypedValue.COMPLEX_UNIT_DIP
                )
                rsiViewActivelyBuyText.setAutoSizeTextTypeUniformWithConfiguration(
                    12, 16, 2, TypedValue.COMPLEX_UNIT_DIP
                )
            } else {
                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                    rsiViewActivelySellText, 12, 16, 1,
                    TypedValue.COMPLEX_UNIT_DIP
                )
                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                    rsiViewSellText, 12, 16, 1,
                    TypedValue.COMPLEX_UNIT_DIP
                )
                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                    rsiViewNeutralText, 12, 16, 1,
                    TypedValue.COMPLEX_UNIT_DIP
                )
                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                    rsiViewBuyText, 12, 16, 1,
                    TypedValue.COMPLEX_UNIT_DIP
                )
                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                    rsiViewActivelyBuyText, 12, 16, 1,
                    TypedValue.COMPLEX_UNIT_DIP
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun setIndicator(progress: Int) {
        if (progress in 0..100) {
            var rsiColorSeekBarThumb = 0
            val rsiColorInactive = ContextCompat.getColor(context, R.color.rsi_inactive)
            val rsiColorActivelySell = ContextCompat.getColor(context, R.color.rsi_actively_sell_text)
            val rsiColorSell = ContextCompat.getColor(context, R.color.rsi_sell_text)
            val rsiColorNeutral = ContextCompat.getColor(context, R.color.rsi_neutral_text)
            val rsiColorBuy = ContextCompat.getColor(context, R.color.rsi_buy_text)
            val rsiColorActivelyBuy = ContextCompat.getColor(context, R.color.rsi_actively_buy_text)
            when (progress) {
                in 0..4 -> {
                    rsiColorSeekBarThumb = R.color.rsi_5
                    rsiViewActivelySellText.setTextColor(rsiColorActivelySell)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)

                }
                in 5..9 -> {
                    rsiColorSeekBarThumb = R.color.rsi_10
                    rsiViewActivelySellText.setTextColor(rsiColorActivelySell)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 10..14 -> {
                    rsiColorSeekBarThumb = R.color.rsi_15
                    rsiViewActivelySellText.setTextColor(rsiColorActivelySell)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 15..19 -> {
                    rsiColorSeekBarThumb = R.color.rsi_20
                    rsiViewActivelySellText.setTextColor(rsiColorActivelySell)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 20..24 -> {
                    rsiColorSeekBarThumb = R.color.rsi_25
                    rsiViewSellText.setTextColor(rsiColorSell)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 25..29 -> {
                    rsiColorSeekBarThumb = R.color.rsi_30
                    rsiViewSellText.setTextColor(rsiColorSell)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 30..34 -> {
                    rsiColorSeekBarThumb = R.color.rsi_35
                    rsiViewSellText.setTextColor(rsiColorSell)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 35..39 -> {
                    rsiColorSeekBarThumb = R.color.rsi_40
                    rsiViewSellText.setTextColor(rsiColorSell)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 40..44 -> {
                    rsiColorSeekBarThumb = R.color.rsi_45
                    rsiViewNeutralText.setTextColor(rsiColorNeutral)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 45..49 -> {
                    rsiColorSeekBarThumb = R.color.rsi_50
                    rsiViewNeutralText.setTextColor(rsiColorNeutral)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 50..54 -> {
                    rsiColorSeekBarThumb = R.color.rsi_55
                    rsiViewNeutralText.setTextColor(rsiColorNeutral)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 55..59 -> {
                    rsiColorSeekBarThumb = R.color.rsi_60
                    rsiViewNeutralText.setTextColor(rsiColorNeutral)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 60..64 -> {
                    rsiColorSeekBarThumb = R.color.rsi_65
                    rsiViewBuyText.setTextColor(rsiColorBuy)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 65..69 -> {
                    rsiColorSeekBarThumb = R.color.rsi_70
                    rsiViewBuyText.setTextColor(rsiColorBuy)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 70..74 -> {
                    rsiColorSeekBarThumb = R.color.rsi_75
                    rsiViewBuyText.setTextColor(rsiColorBuy)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 75..79 -> {
                    rsiColorSeekBarThumb = R.color.rsi_80
                    rsiViewBuyText.setTextColor(rsiColorBuy)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewActivelyBuyText.setTextColor(rsiColorInactive)
                }
                in 80..84 -> {
                    rsiColorSeekBarThumb = R.color.rsi_85
                    rsiViewActivelyBuyText.setTextColor(rsiColorActivelyBuy)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                }
                in 85..89 -> {
                    rsiColorSeekBarThumb = R.color.rsi_90
                    rsiViewActivelyBuyText.setTextColor(rsiColorActivelyBuy)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                }
                in 90..94 -> {
                    rsiColorSeekBarThumb = R.color.rsi_95
                    rsiViewActivelyBuyText.setTextColor(rsiColorActivelyBuy)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                }
                in 95..100 -> {
                    rsiColorSeekBarThumb = R.color.rsi_100
                    rsiViewActivelyBuyText.setTextColor(rsiColorActivelyBuy)
                    rsiViewActivelySellText.setTextColor(rsiColorInactive)
                    rsiViewSellText.setTextColor(rsiColorInactive)
                    rsiViewNeutralText.setTextColor(rsiColorInactive)
                    rsiViewBuyText.setTextColor(rsiColorInactive)
                }
            }

            val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_rsi_arrow)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, rsiColorSeekBarThumb))
            if (rsiSeekBarThumb != null) {
                rsiViewSeekBar.thumb = wrappedDrawable
            }
            rsiViewSeekBar.progress = progress
        } else {
            rsiViewSeekBar.visibility = View.GONE
        }
    }
}