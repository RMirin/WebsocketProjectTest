package com.websocket.project.ui.main

import android.os.Bundle
import com.websocket.project.R
import com.websocket.project.ui.custom.rsi_view.RsiView
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, CryptoPairFragment.newInstance())
//                .commitNow()
//        }
        //Example of usage of RsiView
//        val rsiView = findViewById<RsiView>(R.id.main_rsi_view)
//        rsiView.setIndicator(progress = 10)
    }
}