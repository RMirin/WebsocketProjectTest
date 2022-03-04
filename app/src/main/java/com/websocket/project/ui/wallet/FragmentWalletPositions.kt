package com.websocket.project.ui.wallet

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.R
import com.websocket.project.databinding.FragmentWalletPositionsBinding
import com.websocket.project.ui.base.BaseFragment

class FragmentWalletPositions : BaseFragment<FragmentWalletPositionsBinding>() {

    private val walletPositionsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        WalletPositionsAdapter(
            mutableListOf(
                WalletPositionsModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "x100",
                    WalletSideModel.LONG,
                    "2 060.00 \$",
                    "+24.00 \$"
                ),
                WalletPositionsModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "x100",
                    WalletSideModel.LONG,
                    "2 060.00 \$",
                    "+24.00 \$"
                ),
                WalletPositionsModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "x100",
                    WalletSideModel.LONG,
                    "2 060.00 \$",
                    "+24.00 \$"
                ),
                WalletPositionsModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "x100",
                    WalletSideModel.LONG,
                    "2 060.00 \$",
                    "+24.00 \$"
                ),
                WalletPositionsModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "x100",
                    WalletSideModel.LONG,
                    "2 060.00 \$",
                    "+24.00 \$"
                ),
                WalletPositionsModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "x100",
                    WalletSideModel.LONG,
                    "2 060.00 \$",
                    "+24.00 \$"
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.walletPositionsRecycler.layoutManager =
            object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

        binding.walletPositionsRecycler.adapter = walletPositionsAdapter
    }

    fun showBalance(show: Boolean) {
        walletPositionsAdapter.showBalance(show)
    }

    override fun initViewBinding(): FragmentWalletPositionsBinding =
        FragmentWalletPositionsBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): FragmentWalletPositions {
            return FragmentWalletPositions()
        }
    }
}