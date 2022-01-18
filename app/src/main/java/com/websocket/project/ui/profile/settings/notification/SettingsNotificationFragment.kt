package com.websocket.project.ui.profile.settings.notification

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.websocket.project.databinding.FragmentSettingsNotificationBinding
import com.websocket.project.ui.base.BaseFragment

class SettingsNotificationFragment : BaseFragment<FragmentSettingsNotificationBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            settingsNotificationBackBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            notificationRecycler.adapter = SettingsNotificationAdapter(NotificationItem.values())
        }

    }

    override fun initViewBinding(): FragmentSettingsNotificationBinding =
        FragmentSettingsNotificationBinding.inflate(layoutInflater)

}