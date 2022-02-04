package com.websocket.project.ui.message

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.FragmentMessageBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.util.setMaxVisibleLine

class MessageFragment : BaseFragment<FragmentMessageBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            messageGeneralTextView.setMaxVisibleLine() //use extension from util/Ext
            messageShowMoreBtn.setOnClickListener {
                messageCentreHorizontalGuideline.setGuidelinePercent(1f)
                messageShowMoreBtn.visibility = View.INVISIBLE
                messageGeneralTextView.apply {
                    isVerticalScrollBarEnabled = true
                    movementMethod = ScrollingMovementMethod()
                    maxLines = Integer.MAX_VALUE
                }
            }
        }
    }

    override fun initViewBinding(): FragmentMessageBinding =
        FragmentMessageBinding.inflate(layoutInflater)

}