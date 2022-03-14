package com.websocket.project.ui.support

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.websocket.project.R
import com.websocket.project.databinding.DialogSupportFragmentActionBinding
import com.websocket.project.ui.base.convertDpToPixel
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.support.files.SupportFragmentFileType

class DialogFragmentSupportAction(
    private val messageText: String,
    private val listener: DialogFragmentSupportActionListener,
    private val supportAction: SupportAction,
    private val fileToDelete: SupportFragmentFileType?
) : DialogFragment() {

    lateinit var binding: DialogSupportFragmentActionBinding

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        val param: WindowManager.LayoutParams? = dialog?.window?.attributes
        param?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        param?.width = ViewGroup.LayoutParams.MATCH_PARENT
        val back = ColorDrawable(Color.TRANSPARENT)
        val insetFloat = convertDpToPixel(16f, activity as MainActivity)
        val inset = InsetDrawable(back, insetFloat)

        dialog?.window?.setBackgroundDrawable(inset)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogSupportFragmentActionBinding.bind(inflater.inflate(R.layout.dialog_support_fragment_action, container))

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

        return binding.root
    }
}