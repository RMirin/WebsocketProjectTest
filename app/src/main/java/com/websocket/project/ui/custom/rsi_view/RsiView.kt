package com.websocket.project.ui.custom.rsi_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.websocket.project.R

class RsiView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var rsiViewActivelySellText: TextView
    private lateinit var rsiViewSellText: TextView
    private lateinit var rsiViewNeutralText: TextView
    private lateinit var rsiViewBuyText: TextView
    private lateinit var rsiViewActivelyBuyText: TextView
    private lateinit var rsiViewActivelySellImg: ImageView
    private lateinit var rsiViewSellImg: ImageView
    private lateinit var rsiViewNeutralImg: ImageView
    private lateinit var rsiViewBuyImg: ImageView
    private lateinit var rsiViewActivelyBuyImg: ImageView

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.rsi_view, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.RsiView)

        try {
            rsiViewActivelySellText = findViewById(R.id.layout_rsi_actively_sell_text)
            rsiViewSellText = findViewById(R.id.layout_rsi_sell_text)
            rsiViewNeutralText = findViewById(R.id.layout_rsi_neutral_text)
            rsiViewBuyText = findViewById(R.id.layout_rsi_buy_text)
            rsiViewActivelyBuyText = findViewById(R.id.layout_rsi_actively_buy_text)

            rsiViewActivelySellImg = findViewById(R.id.layout_rsi_actively_sell_img)
            rsiViewSellImg = findViewById(R.id.layout_rsi_sell_img)
            rsiViewNeutralImg = findViewById(R.id.layout_rsi_neutral_img)
            rsiViewBuyImg = findViewById(R.id.layout_rsi_buy_img)
            rsiViewActivelyBuyImg = findViewById(R.id.layout_rsi_actively_buy_img)

            rsiViewActivelySellText.text = context.getText(RsiIndicator.ACTIVELY_SELL.rsiText)
            rsiViewSellText.text = context.getText(RsiIndicator.SELL.rsiText)
            rsiViewNeutralText.text = context.getText(RsiIndicator.NEUTRAL.rsiText)
            rsiViewBuyText.text = context.getText(RsiIndicator.BUY.rsiText)
            rsiViewActivelyBuyText.text = context.getText(RsiIndicator.ACTIVELY_BUY.rsiText)
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
        } finally {
            ta.recycle()
        }
    }

    fun activateIndicator(indicator: RsiIndicator) {
        when (indicator) {
            RsiIndicator.ACTIVELY_SELL -> {
                rsiViewActivelySellImg.visibility = View.VISIBLE
                rsiViewActivelySellText.setTextColor(ContextCompat.getColor(context, indicator.rsiColor))
            }
            RsiIndicator.SELL -> {
                rsiViewSellImg.visibility = View.VISIBLE
                rsiViewSellText.setTextColor(ContextCompat.getColor(context, indicator.rsiColor))
            }
            RsiIndicator.NEUTRAL -> {
                rsiViewNeutralImg.visibility = View.VISIBLE
                rsiViewNeutralText.setTextColor(ContextCompat.getColor(context, indicator.rsiColor))
            }
            RsiIndicator.BUY -> {
                rsiViewBuyImg.visibility = View.VISIBLE
                rsiViewBuyText.setTextColor(ContextCompat.getColor(context, indicator.rsiColor))
            }
            RsiIndicator.ACTIVELY_BUY -> {
                rsiViewActivelyBuyImg.visibility = View.VISIBLE
                rsiViewActivelyBuyText.setTextColor(ContextCompat.getColor(context, indicator.rsiColor))
            }
        }
    }
}