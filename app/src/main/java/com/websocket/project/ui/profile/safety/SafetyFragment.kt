package com.websocket.project.ui.profile.safety

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.websocket.project.R
import com.websocket.project.databinding.FragmentSafetyBinding
import com.websocket.project.ui.base.BaseFragment

class SafetyFragment : BaseFragment<FragmentSafetyBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            safetyBackBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            safetyRecycler.adapter = SafetyItemAdapter(SafetyItem.values())
        }
    }

    override fun initViewBinding(): FragmentSafetyBinding =
        FragmentSafetyBinding.inflate(layoutInflater)

}