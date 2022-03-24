package com.websocket.project.ui.base

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

abstract class BaseDrawerListener: DrawerLayout.DrawerListener {
    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

    override fun onDrawerOpened(drawerView: View) {}

    override fun onDrawerStateChanged(newState: Int) {}
}