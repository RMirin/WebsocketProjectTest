package com.websocket.project.ui.profile.settings.settings_main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.websocket.project.R
import com.websocket.project.databinding.FragmentSettingsMainBinding
import com.websocket.project.ui.base.BaseFragment

class SettingsMainFragment : BaseFragment<FragmentSettingsMainBinding>(), SettingsMainItemOnClick {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            settingsBackBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            settingsMainRecycler.adapter =
                SettingsMainItemAdapter(SettingsItem.values(), this@SettingsMainFragment)
        }
    }

    override fun initViewBinding(): FragmentSettingsMainBinding =
        FragmentSettingsMainBinding.inflate(layoutInflater)

    override fun itemClick(settingsItem: SettingsItem) {
        when (settingsItem) {
            SettingsItem.LANGUAGE -> {
                findNavController().navigate(R.id.action_settingsMainFragment_to_settingsLanguageFragment)
            }
            SettingsItem.NOTIFICATION -> {
                findNavController().navigate(R.id.action_settingsMainFragment_to_settingsNotificationFragment)
            }
        }
    }

}