package com.websocket.project.ui.kyc

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.websocket.project.R
import com.websocket.project.databinding.BottomSheetKycDatePickerBinding
import com.websocket.project.ui.base.BaseBottomSheetDialogFragment
import com.websocket.project.ui.custom.datepicker.DatePickerDate
import com.websocket.project.ui.network.NetworkBottomSheetFragment

class KycDatePickerBottomSheetFragment :
    BaseBottomSheetDialogFragment<BottomSheetKycDatePickerBinding>(), KycDatePickerActionListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            setBottomSheetExpanded(dialog)
        }
        return dialog
    }

    private fun setBottomSheetExpanded(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        bottomSheet?.let {
            val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
            val layoutParams = bottomSheet.layoutParams
            bottomSheet.layoutParams = layoutParams
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = false
        }

    }

    override fun initViewBinding(): BottomSheetKycDatePickerBinding =
        BottomSheetKycDatePickerBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            kycDatePickerActionListenerBinding = this@KycDatePickerBottomSheetFragment
        }
    }

    override fun readyBtnClick() {
        val chosenDate = DatePickerDate(
            binding.bottomSheetKycDatePicker.dayChosen,
            binding.bottomSheetKycDatePicker.monthChosen,
            binding.bottomSheetKycDatePicker.yearChosen
        )
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            DATE_PICKER_RESULT_KEY,
            chosenDate
        )
        dismiss()
    }

    companion object {
        const val DATE_PICKER_RESULT_KEY = "datepicker"
    }
}