package com.websocket.project.ui.alert

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.websocket.project.databinding.DialogFragmentAlertBinding
import com.websocket.project.ui.base.BaseDialogFragment
import com.websocket.project.ui.base.getLocalizedResources
import java.util.*

class DialogFragmentAlert: BaseDialogFragment<DialogFragmentAlertBinding>(),
    DialogFragmentAlertActionListener {

    private lateinit var localizedResources: Resources

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: DialogFragmentAlertArgs by navArgs()

        with(binding) {

            val alertType = args.alertType

            localizedResources = getLocalizedResources(requireContext(), Locale(args.localeCode))

            dialogFragmentAlertActionListenerBinding = this@DialogFragmentAlert

            imgVisibleBinding = when (alertType) {
                AlertType.IMAGE_SAVED -> false
                else -> true
            }

            dialogFragmentAlertTitleText.text = localizedResources.getText(alertType.titleId)
            if (alertType.msgId != null) {
                dialogFragmentAlertMsgText.text = localizedResources.getText(alertType.msgId)
            }

            if (alertType.iconId != null) {
                dialogFragmentAlertMsgText.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(requireContext(), alertType.iconId), null, null)
            }
        }
    }

    override fun initViewBinding(): DialogFragmentAlertBinding =
        DialogFragmentAlertBinding.inflate(layoutInflater)

    override fun onOkClick() {
        dismiss()
    }
}