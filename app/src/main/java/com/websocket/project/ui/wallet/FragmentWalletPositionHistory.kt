package com.websocket.project.ui.wallet

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.R
import com.websocket.project.databinding.FragmentWalletPositionHistoryBinding
import com.websocket.project.ui.base.BaseFragment

class FragmentWalletPositionHistory : BaseFragment<FragmentWalletPositionHistoryBinding>() {

    private val walletPositionHistoryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        WalletPositionHistoryAdapter(
            mutableListOf(
                WalletPositionHistoryModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "Bitcoin",
                    "x100",
                    0L,
                    0L,
                    "200$",
                    WalletSideModel.LONG
                ),
                WalletPositionHistoryModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "Bitcoin",
                    "x100",
                    0L,
                    0L,
                    "200$",
                    WalletSideModel.LONG
                ),
                WalletPositionHistoryModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "Bitcoin",
                    "x100",
                    0L,
                    0L,
                    "200$",
                    WalletSideModel.LONG
                ),
                WalletPositionHistoryModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "Bitcoin",
                    "x100",
                    0L,
                    0L,
                    "200$",
                    WalletSideModel.LONG
                ),
                WalletPositionHistoryModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "Bitcoin",
                    "x100",
                    0L,
                    0L,
                    "200$",
                    WalletSideModel.LONG
                ),
                WalletPositionHistoryModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "Bitcoin",
                    "x100",
                    0L,
                    0L,
                    "200$",
                    WalletSideModel.LONG
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.walletPositionHistoryRecycler.layoutManager =
            object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

        binding.walletPositionHistoryRecycler.adapter = walletPositionHistoryAdapter
    }

    fun showBalance(show: Boolean) {
        walletPositionHistoryAdapter.showBalance(show)
    }

    override fun initViewBinding(): FragmentWalletPositionHistoryBinding =
        FragmentWalletPositionHistoryBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): FragmentWalletPositionHistory {
            return FragmentWalletPositionHistory()
        }
    }
}