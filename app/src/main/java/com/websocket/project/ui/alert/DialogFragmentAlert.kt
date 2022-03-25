package com.websocket.project.ui.alert

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.websocket.project.databinding.DialogFragmentAlertBinding
import com.websocket.project.ui.base.BaseDialogFragment

class DialogFragmentAlert : BaseDialogFragment<DialogFragmentAlertBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: DialogFragmentAlertArgs by navArgs()

        with(binding) {

            titleTextBinding = args.titleString

            dialogFragmentAlertOkBtn.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun initViewBinding(): DialogFragmentAlertBinding =
        DialogFragmentAlertBinding.inflate(layoutInflater)
}