package com.websocket.project.ui.withdraw

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.websocket.project.R
import com.websocket.project.databinding.FragmentWithdrawBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.base.observe
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.withdraw.network.WithdrawNetwork
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawFragment :
    BaseFragment<FragmentWithdrawBinding>(), WithdrawFragmentActionListener {

    private val viewModel: WithdrawViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(
            NETWORK_RESULT_KEY
        )?.observe(viewLifecycleOwner) { result ->
            viewModel.setSelectedNetwork(result)
        }

        with(binding) {
            withdrawTopbarLayoutInclude.backClick = View.OnClickListener {
                (activity as MainActivity).onBackPressed()
            }
            withdrawTopbarLayoutInclude.title.text =
                requireContext().getString(R.string.withdraw_title)

            withdrawViewModelBinding = viewModel
            withdrawFragmentActionListenerBinding = this@WithdrawFragment

            withdrawAddressToSendEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.setAddress(text.toString())
            }

            withdrawAmountEditText.doOnTextChanged { text, _, _, _ ->
                val withdrawAmount: Int = if (text.isNullOrEmpty()) {
                    0
                } else {
                    Integer.valueOf(text.toString())
                }
                viewModel.setAvailableUsdt(withdrawAmount)
            }
        }

        observeLiveData()
    }

    override fun initViewBinding(): FragmentWithdrawBinding =
        FragmentWithdrawBinding.inflate(layoutInflater)

    override fun onWithdrawNetworkBtnClicked() {
        findNavController().navigate(WithdrawFragmentDirections.actionWithdrawFragmentToWithdrawNetworkBottomSheetFragment())
    }

    override fun onAddressScanBtnClicked() {

    }

    override fun onAddressClearBtnClicked() {
        binding.withdrawAddressToSendEditText.text.clear()
    }

    override fun onAmountMaxBtnClicked() {
        val withdrawAmount = viewModel.onAmountMaxBtnClicked().toString()
        with(binding.withdrawAmountEditText) {
            setText(withdrawAmount, TextView.BufferType.EDITABLE)
            setSelection(withdrawAmount.length)
        }
    }

    override fun onAmountClearBtnClicked() {
        binding.withdrawAmountEditText.text.clear()
    }

    private fun observeLiveData() {
        with(viewModel) {
            observe(networkChosenPosition) { networkChosenPosition ->
                if (networkChosenPosition != -1) {
                    val withdrawNetwork = WithdrawNetwork.values()[networkChosenPosition]
                    val name = requireContext().getString(withdrawNetwork.networkName)
                    val code = requireContext().getString(withdrawNetwork.networkCode)
                    with(binding) {
                        withdrawNetworkBtn.text = requireContext().getString(R.string.withdraw_network_name, name, code)
                        withdrawNetworkBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        withdrawAttentionMsgText.text = requireContext().getString(R.string.withdraw_attention_msg, code, name)
                        withdrawArriveMsgText.text = requireContext().getString(R.string.withdraw_arrive_msg, code)
                    }
                }
            }

            observe(transactionFeeTotal) { transactionFeeTotal ->
                with(binding) {
                    withdrawTransactionFeeTotalText.text = requireContext().getString(R.string.withdraw_usdt, transactionFeeTotal)
                }
            }
        }
    }

    companion object {
        const val NETWORK_RESULT_KEY = "network"
    }
}