package com.websocket.project.ui.custom.rsi_view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
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
            rsiSeekBarThumb = ContextCompat.getDrawable(context, R.drawable.ic_rsi_arrow)

            rsiViewSeekBar.isEnabled = false
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun setIndicator(progress: Int) {
        if (progress in 0..100) {
            var rsiColor = 0
            when (progress) {
                in 0..4 -> {
                    rsiColor = R.color.rsi_5
                    rsiViewActivelySellText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 5..9 -> {
                    rsiColor = R.color.rsi_10
                    rsiViewActivelySellText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 10..14 -> {
                    rsiColor = R.color.rsi_15
                    rsiViewActivelySellText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 15..19 -> {
                    rsiColor = R.color.rsi_20
                    rsiViewActivelySellText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 20..24 -> {
                    rsiColor = R.color.rsi_25
                    rsiViewSellText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 25..29 -> {
                    rsiColor = R.color.rsi_30
                    rsiViewSellText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 30..34 -> {
                    rsiColor = R.color.rsi_35
                    rsiViewSellText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 35..39 -> {
                    rsiColor = R.color.rsi_40
                    rsiViewSellText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 40..44 -> {
                    rsiColor = R.color.rsi_45
                    rsiViewNeutralText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 45..49 -> {
                    rsiColor = R.color.rsi_50
                    rsiViewNeutralText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 50..54 -> {
                    rsiColor = R.color.rsi_55
                    rsiViewNeutralText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 55..59 -> {
                    rsiColor = R.color.rsi_60
                    rsiViewNeutralText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 60..64 -> {
                    rsiColor = R.color.rsi_65
                    rsiViewBuyText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 65..69 -> {
                    rsiColor = R.color.rsi_70
                    rsiViewBuyText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 70..74 -> {
                    rsiColor = R.color.rsi_75
                    rsiViewBuyText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 75..79 -> {
                    rsiColor = R.color.rsi_80
                    rsiViewBuyText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 80..84 -> {
                    rsiColor = R.color.rsi_85
                    rsiViewActivelyBuyText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 85..89 -> {
                    rsiColor = R.color.rsi_90
                    rsiViewActivelyBuyText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 90..94 -> {
                    rsiColor = R.color.rsi_95
                    rsiViewActivelyBuyText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
                in 95..100 -> {
                    rsiColor = R.color.rsi_100
                    rsiViewActivelyBuyText.setTextColor(ContextCompat.getColor(context, rsiColor))
                }
            }

            val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_rsi_arrow)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, rsiColor))
            if (rsiSeekBarThumb != null) {
                rsiViewSeekBar.thumb = wrappedDrawable
            }
            rsiViewSeekBar.progress = progress
        } else {
            rsiViewSeekBar.visibility = View.GONE
        }
    }
}