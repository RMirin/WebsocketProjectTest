package com.websocket.project.ui.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.FragmentMessageWithoutHideBinding
import com.websocket.project.ui.base.BaseFragment

class MessageWithoutHideFragment : BaseFragment<FragmentMessageWithoutHideBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun initViewBinding(): FragmentMessageWithoutHideBinding =
        FragmentMessageWithoutHideBinding.inflate(layoutInflater)

}