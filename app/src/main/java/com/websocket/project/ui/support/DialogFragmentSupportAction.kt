package com.websocket.project.ui.support

import android.os.Bundle
import android.view.View
import com.websocket.project.databinding.DialogSupportFragmentActionBinding
import com.websocket.project.ui.base.BaseDialogFragment
import com.websocket.project.ui.support.files.SupportFragmentFileType

class DialogFragmentSupportAction(
    private val messageText: String,
    private val listener: DialogFragmentSupportActionListener,
    private val supportAction: SupportAction,
    private val fileToDelete: SupportFragmentFileType?
) : BaseDialogFragment<DialogSupportFragmentActionBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            dialogSupportFragmentMessageBinding = messageText
            dialogSupportFragmentActionListenerBinding = listener
            dialogSupportFragmentActionCancelBtn.setOnClickListener {
                dismiss()
            }

            dialogSupportFragmentActionOkBtn.setOnClickListener {
                when(supportAction) {
                    SupportAction.DELETE_TEXT_OK -> listener.deleteTextOfAppealActionOk()
                    SupportAction.DELETE_FILES_OK -> listener.deleteAllFilesActionOk()
                    SupportAction.DELETE_FILE_OK -> {
                        if (fileToDelete != null) {
                            listener.deleteFileActionOk(fileToDelete)
                        }
                    }
                }
                dismiss()
            }
        }
    }

    override fun initViewBinding(): DialogSupportFragmentActionBinding =
        DialogSupportFragmentActionBinding.inflate(layoutInflater)
}