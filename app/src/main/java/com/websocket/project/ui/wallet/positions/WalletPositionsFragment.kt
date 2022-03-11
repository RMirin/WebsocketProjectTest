package com.websocket.project.ui.wallet.positions

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.R
import com.websocket.project.databinding.FragmentWalletPositionsBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.wallet.WalletSideModel

class WalletPositionsFragment : BaseFragment<FragmentWalletPositionsBinding>() {

    private val walletPositionsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        WalletPositionsAdapter(
            mutableListOf(
                WalletPositionsModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "x100",
                    WalletSideModel.LONG,
                    2060.00f,
                    24.00f
                ),
                WalletPositionsModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "x100",
                    WalletSideModel.LONG,
                    2060.00f,
                    24.00f
                ),
                WalletPositionsModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "x100",
                    WalletSideModel.LONG,
                    2060.00f,
                    24.00f
                ),
                WalletPositionsModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "x100",
                    WalletSideModel.LONG,
                    2060.00f,
                    24.00f
                ),
                WalletPositionsModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "x100",
                    WalletSideModel.LONG,
                    2060.00f,
                    24.00f
                ),
                WalletPositionsModel(
                    R.drawable.ic_crypto_btc,
                    "Btc",
                    "x100",
                    WalletSideModel.LONG,
                    2060.00f,
                    24.00f
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
        with(binding.walletPositionsRecycler) {
            layoutManager =
                object : LinearLayoutManager(requireContext()) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }

            adapter = walletPositionsAdapter
        }
    }

    fun showBalance(show: Boolean) {
        walletPositionsAdapter.showBalance(show)
    }

    override fun initViewBinding(): FragmentWalletPositionsBinding =
        FragmentWalletPositionsBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): WalletPositionsFragment {
            return WalletPositionsFragment()
        }
    }
}