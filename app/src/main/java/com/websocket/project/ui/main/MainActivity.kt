package com.websocket.project.ui.main

import android.os.Bundle
import com.websocket.project.R
import com.websocket.project.ui.custom.rsi_view.RsiView
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.websocket.project.ui.base.setupWithNavController
import com.websocket.project.ui.custom.rsi_view.RsiIndicator

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

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
//        rsiView.activateIndicator(RsiIndicator.BUY)
    }

    private fun initBottomNavigation() {

        bottomNavigationView = findViewById(R.id.main_bottom_nav_view)

        bottomNavigationView.setupWithNavController(
            navGraphIds = listOf(
                R.navigation.nav_graph_main,
                R.navigation.nav_graph_markets,
                R.navigation.nav_graph_trade,
                R.navigation.nav_graph_wallet
            ),
            fragmentManager = supportFragmentManager,
            containerId = R.id.main_nav_host_container,
            intent = intent
        )
    }
}