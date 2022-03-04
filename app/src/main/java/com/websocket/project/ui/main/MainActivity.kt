package com.websocket.project.ui.main

import android.os.Bundle
import com.websocket.project.R
import com.websocket.project.ui.custom.rsi_view.RsiView
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import com.websocket.project.ui.custom.risk_level_view.RiskLevel
import com.websocket.project.ui.custom.risk_level_view.RiskLevelView
import com.websocket.project.ui.custom.rsi_view.RsiIndicator
import com.websocket.project.ui.main.crypto.MainFragment
import com.websocket.project.ui.main.splash.SplashFragment
import com.websocket.project.ui.transaction_history.TransactionHistoryFragment
import com.websocket.project.ui.wallet.WalletFragment


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, WalletFragment.newInstance())
                .commitNow()
        }
        //Example of usage of RsiView
//        val rsiView = findViewById<RsiView>(R.id.main_rsi_view)
//        rsiView.activateIndicator(RsiIndicator.BUY)
        //Example of usage of RiskLevelView
//        val riskLevelView = findViewById<RiskLevelView>(R.id.main_risk_level_view)
//        riskLevelView.setRiskLevel(RiskLevel.HIGH)
    }
}