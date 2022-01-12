package com.websocket.project.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.websocket.project.ui.base.BaseActivity
import com.websocket.project.R
import com.websocket.project.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}