package com.websocket.project.ui.profile.profile_main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.FragmentProfileBinding
import com.websocket.project.ui.base.BaseFragment
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(), ProfileItemClick {

    private val viewModel:ProfileViewModel by viewModels()

    val email = "Example@gmail.com"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            profileEmailTv.text = email
            profileItemRecycler.adapter =
                ProfileItemAdapter(ProfileItem.values(), this@ProfileFragment)
            profileCopyIdBtn.setOnClickListener {
                copyIdToClipboard()
            }
            profileViewHideCheckBox.setOnCheckedChangeListener { _, isChecked ->
                profileEmailTv.text = when (isChecked) {
                    true -> viewModel.hideEmail(email)
                    false -> email
                }
            }
        }

    }

    private fun copyIdToClipboard() {
        val clipboard =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val id = binding.profileIdTv.text.toString()
        val clip = ClipData.newPlainText("", id)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "Your $id copy to clipboard", Toast.LENGTH_SHORT).show()
    }

    override fun itemClick(profileItem: ProfileItem) {
        when (profileItem) {
            ProfileItem.SETTINGS -> {
                findNavController().navigate(R.id.action_profileFragment_to_settingsMainFragment)
            }
            ProfileItem.SAFETY -> {
                findNavController().navigate(R.id.action_profileFragment_to_safetyFragment)
            }
            ProfileItem.NOTIFICATION -> {}
            ProfileItem.SUPPORT -> {}
        }
    }

    override fun initViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)
}