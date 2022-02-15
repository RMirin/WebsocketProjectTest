package com.websocket.project.ui.main.splash

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.websocket.project.R
import com.websocket.project.databinding.FragmentSplashBinding
import com.websocket.project.ui.base.BaseFragment

class SplashFragment: BaseFragment<FragmentSplashBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Start animation
        val ad = ResourcesCompat.getDrawable(
            resources,
            R.drawable.bg_splash_animation_list,
            null
        ) as AnimationDrawable
        binding.splashAnimationImg.setImageDrawable(ad)
        ad.start()
    }

    override fun initViewBinding(): FragmentSplashBinding =
        FragmentSplashBinding.inflate(layoutInflater)

    companion object {
        fun newInstance() = SplashFragment()
    }
}