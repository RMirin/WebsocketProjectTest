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
import com.websocket.project.ui.base.getLocalizedResources
import com.websocket.project.ui.base.observe
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.network.Network
import com.websocket.project.ui.network.NetworkBottomSheetFragment.Companion.NETWORK_RESULT_KEY
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

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

        val localizedResources = getLocalizedResources(requireContext(), Locale("ru"))

        with(binding) {
            withdrawTopbarLayoutInclude.backClick = View.OnClickListener {
                (activity as MainActivity).onBackPressed()
            }

            withdrawTopbarLayoutInclude.title.text =
                localizedResources?.getString(R.string.withdraw_title)
            withdrawNetworkTitleText.text =
                localizedResources?.getString(R.string.withdraw_network_title)
            withdrawNetworkBtn.text = localizedResources?.getString(R.string.withdraw_network_hint)
            withdrawAddressToSendUsdtTitleText.text =
                localizedResources?.getString(R.string.withdraw_address_to_send_usdt_title)
            withdrawAddressToSendEditText.hint =
                localizedResources?.getString(R.string.withdraw_address_to_send_usdt_hint)
            withdrawAmountEditText.hint =
                localizedResources?.getString(R.string.withdraw_amount_hint)
            withdrawAmountUsdtTitleText.text =
                localizedResources?.getString(R.string.withdraw_amount_usdt_title)
            withdrawAmountUsdtErrorText.text =
                localizedResources?.getString(R.string.withdraw_amount_usdt_error)
            withdrawAvailableUsdtText.text = localizedResources?.getString(
                R.string.withdraw_available_usdt,
                viewModel.getAvailableUsdt()
            )
            withdrawTransactionFeeNetworkTitleText.text =
                localizedResources?.getString(R.string.withdraw_transaction_fee_network)
            withdrawTransactionFeeTotalTitleText.text =
                localizedResources?.getString(R.string.withdraw_transaction_fee_total)
            withdrawFeeText.text = localizedResources?.getString(R.string.withdraw_fee_msg)
            withdrawSendBtn.text = localizedResources?.getString(R.string.withdraw_send)

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
        //Todo: scan address QR
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
        val localizedResources = getLocalizedResources(requireContext(), Locale("ru"))
        with(viewModel) {
            observe(networkChosenPosition) { networkChosenPosition ->
                if (networkChosenPosition != -1) {
                    val withdrawNetwork = Network.values()[networkChosenPosition]
                    val name = requireContext().getString(withdrawNetwork.networkName)
                    val code = requireContext().getString(withdrawNetwork.networkCode)
                    with(binding) {
                        withdrawNetworkBtn.text = localizedResources?.getString(R.string.withdraw_network_btn, name, code)
                        withdrawNetworkBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        withdrawAttentionText.text =  localizedResources?.getString(R.string.withdraw_attention_msg, code, name)
                        withdrawArriveText.text = localizedResources?.getString(R.string.withdraw_arrive_msg, code)
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
}