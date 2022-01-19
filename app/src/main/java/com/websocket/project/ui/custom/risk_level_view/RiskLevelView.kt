package com.websocket.project.ui.custom.risk_level_view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.CheckedTextView
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.websocket.project.R
import java.lang.RuntimeException

class RiskLevelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var viewRiskLevelLowText: CheckedTextView
    private lateinit var viewRiskLevelMediumText: CheckedTextView
    private lateinit var viewRiskLevelHighText: CheckedTextView
    private lateinit var viewRiskLevelLowShadowImg: ImageView
    private lateinit var viewRiskLevelMediumShadowImg: ImageView
    private lateinit var viewRiskLevelHighShadowImg: ImageView
    private lateinit var viewRiskLevelLowIndicatorImg: ImageView
    private lateinit var viewRiskLevelMediumIndicatorImg: ImageView
    private lateinit var viewRiskLevelHighIndicatorImg: ImageView
    private lateinit var viewRiskLevelDividerFirstImg: View
    private lateinit var viewRiskLevelDividerSecondImg: View

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.view_risk_level, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.RiskLevelView)

        try {
            viewRiskLevelLowText = findViewById(R.id.view_risk_level_low_checked_text)
            viewRiskLevelMediumText = findViewById(R.id.view_risk_level_medium_checked_text)
            viewRiskLevelHighText = findViewById(R.id.view_risk_level_high_checked_text)

            viewRiskLevelLowShadowImg = findViewById(R.id.view_risk_level_low_shadow_img)
            viewRiskLevelMediumShadowImg = findViewById(R.id.view_risk_level_medium_shadow_img)
            viewRiskLevelHighShadowImg = findViewById(R.id.view_risk_level_high_shadow_img)

            viewRiskLevelLowIndicatorImg = findViewById(R.id.view_risk_level_low_indicator_img)
            viewRiskLevelMediumIndicatorImg = findViewById(R.id.view_risk_level_medium_indicator_img)
            viewRiskLevelHighIndicatorImg = findViewById(R.id.view_risk_level_high_indicator_img)

            viewRiskLevelDividerFirstImg = findViewById(R.id.view_risk_level_divider_first)
            viewRiskLevelDividerSecondImg = findViewById(R.id.view_risk_level_divider_second)
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
        } finally {
            ta.recycle()
        }
    }

    fun setRiskLevel(riskLevel: RiskLevel) {
        when(riskLevel) {
            RiskLevel.LOW -> {
                viewRiskLevelLowText.isChecked = true
                viewRiskLevelLowShadowImg.visibility = View.VISIBLE
                viewRiskLevelLowIndicatorImg.visibility = View.VISIBLE
                viewRiskLevelDividerFirstImg.visibility = View.GONE
            }
            RiskLevel.MEDIUM -> {
                viewRiskLevelMediumText.isChecked = true
                viewRiskLevelMediumShadowImg.visibility = View.VISIBLE
                viewRiskLevelMediumIndicatorImg.visibility = View.VISIBLE
                viewRiskLevelDividerFirstImg.visibility = View.GONE
                viewRiskLevelDividerSecondImg.visibility = View.GONE
            }
            RiskLevel.HIGH -> {
                viewRiskLevelHighText.isChecked = true
                viewRiskLevelHighShadowImg.visibility = View.VISIBLE
                viewRiskLevelHighIndicatorImg.visibility = View.VISIBLE
                viewRiskLevelDividerSecondImg.visibility = View.GONE
            }
        }
    }
}