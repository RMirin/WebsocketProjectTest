package com.websocket.project.ui.wallet.identity_confirmation

import android.os.Bundle
import android.view.View
import com.websocket.project.databinding.DialogFragmentIdentityConfirmationBinding
import com.websocket.project.ui.base.BaseDialogFragment

class DialogFragmentIdentityConfirmation(
    private val dialogTitleText: String,
    private val listener: DialogFragmentIdentityConfirmationListener
) : BaseDialogFragment<DialogFragmentIdentityConfirmationBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            identityConfirmationTitleTextBinding = dialogTitleText
            dialogFragmentIdentityConfirmationListenerBinding = listener

            identityConfirmationCancelBtn.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun initViewBinding(): DialogFragmentIdentityConfirmationBinding =
        DialogFragmentIdentityConfirmationBinding.inflate(layoutInflater)
}