package com.websocket.project.ui.withdraw.network

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.databinding.BottomSheetLayoutWithdrawNetworkBinding
import com.websocket.project.ui.base.BaseBottomSheetDialogFragment
import com.websocket.project.ui.support.SupportFragment
import com.websocket.project.ui.support.appeal_category.AppealCategory
import com.websocket.project.ui.withdraw.WithdrawFragment.Companion.NETWORK_RESULT_KEY

class WithdrawNetworkBottomSheetFragment :
    BaseBottomSheetDialogFragment<BottomSheetLayoutWithdrawNetworkBinding>(),
    WithdrawNetworkBottomSheetActionListener {

    private val withdrawNetworkAdapter by lazy(LazyThreadSafetyMode.NONE) {
        WithdrawNetworkAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            withdrawNetworkBottomSheetLayoutRecycler.adapter = withdrawNetworkAdapter
        }
    }

    override fun initViewBinding(): BottomSheetLayoutWithdrawNetworkBinding =
        BottomSheetLayoutWithdrawNetworkBinding.inflate(layoutInflater)

    override fun onNetworkActionClicked(withdrawNetwork: WithdrawNetwork) {
        val networkPosition = WithdrawNetwork.valueOf(withdrawNetwork.name).ordinal
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            NETWORK_RESULT_KEY,
            networkPosition
        )
        dismiss()
    }
}
