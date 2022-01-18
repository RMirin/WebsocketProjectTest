package com.websocket.project.ui.profile.settings.language

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.websocket.project.R
import com.websocket.project.databinding.FragmentSettingsLanguageBinding
import com.websocket.project.ui.base.BaseFragment

class SettingsLanguageFragment : BaseFragment<FragmentSettingsLanguageBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            settingsLanguageBackBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initViewBinding(): FragmentSettingsLanguageBinding =
        FragmentSettingsLanguageBinding.inflate(layoutInflater)
}