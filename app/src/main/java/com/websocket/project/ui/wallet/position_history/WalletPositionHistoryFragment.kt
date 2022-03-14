package com.websocket.project.ui.wallet.position_history

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.R
import com.websocket.project.databinding.FragmentWalletPositionHistoryBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.transaction_history.TransactionStatus
import com.websocket.project.ui.wallet.WalletSideModel
import com.websocket.project.ui.wallet.position_history.detail.DialogFragmentPositionHistoryDetail

class WalletPositionHistoryFragment : BaseFragment<FragmentWalletPositionHistoryBinding>(),
    WalletPositionHistoryFragmentListener {

    private val walletPositionHistoryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        WalletPositionHistoryAdapter(
            this,
            mutableListOf(
                WalletPositionHistoryModel(
                    positionId = 909883L,
                    icon = R.drawable.ic_crypto_eth,
                    name = "ETH",
                    nameFull = "Etherium",
                    leverage = "x75",
                    side = WalletSideModel.LONG,
                    status = TransactionStatus.CLOSED,
                    openingDate = System.currentTimeMillis(),
                    openingTime = System.currentTimeMillis(),
                    averageSellPrice = 3070.071f,
                    amountBuyCrypto = 0.488f,
                    amountSellUsdt = 1498.195f,
                    closingDate = System.currentTimeMillis(),
                    closingTime = System.currentTimeMillis(),
                    averageBuyPrice = 3039.370f,
                    amountSellCrypto = 0.488f,
                    amountBuyUsdt = 1483.213f,
                    profit = 14.982f,
                    fee = 2.98140735f
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //variable to show/hide recycler and empty state
        binding.showContent = true
        initRecycler()
    }

    private fun initRecycler() {
        with(binding.walletPositionHistoryRecycler) {
            layoutManager =
                object : LinearLayoutManager(requireContext()) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }

            adapter = walletPositionHistoryAdapter
        }
    }

    fun showBalance(show: Boolean) {
        walletPositionHistoryAdapter.showBalance(show)
    }

    override fun initViewBinding(): FragmentWalletPositionHistoryBinding =
        FragmentWalletPositionHistoryBinding.inflate(layoutInflater)

    override fun onWalletPositionHistoryItemClick(walletPositionHistoryModel: WalletPositionHistoryModel) {
        DialogFragmentPositionHistoryDetail(
            walletPositionHistoryModel
        ).show(
            (activity as MainActivity).supportFragmentManager,
            KEY_DIALOG_POSITION_HISTORY_DETAIL
        )
    }

    companion object {
        fun newInstance(): WalletPositionHistoryFragment {
            return WalletPositionHistoryFragment()
        }

        private const val KEY_DIALOG_POSITION_HISTORY_DETAIL = "DialogFragmentPositionHistoryDetail"
    }
}