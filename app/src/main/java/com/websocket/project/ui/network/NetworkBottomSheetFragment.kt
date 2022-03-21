package com.websocket.project.ui.network

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.websocket.project.databinding.BottomSheetLayoutWithdrawNetworkBinding
import com.websocket.project.ui.base.BaseBottomSheetDialogFragment

class NetworkBottomSheetFragment :
    BaseBottomSheetDialogFragment<BottomSheetLayoutWithdrawNetworkBinding>(),
    NetworkBottomSheetActionListener {

    private val withdrawNetworkAdapter by lazy(LazyThreadSafetyMode.NONE) {
        NetworkAdapter(Network.values(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            withdrawNetworkBottomSheetLayoutRecycler.adapter = withdrawNetworkAdapter
        }
    }

    override fun initViewBinding(): BottomSheetLayoutWithdrawNetworkBinding =
        BottomSheetLayoutWithdrawNetworkBinding.inflate(layoutInflater)

    override fun onNetworkActionClicked(network: Network) {
        val networkPosition = Network.valueOf(network.name).ordinal
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            NETWORK_RESULT_KEY,
            networkPosition
        )
        dismiss()
    }

    companion object {
        const val NETWORK_RESULT_KEY = "network"
    }
}
