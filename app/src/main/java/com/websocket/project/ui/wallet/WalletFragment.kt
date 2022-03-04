package com.websocket.project.ui.wallet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.tabs.TabLayoutMediator
import com.websocket.project.R
import com.websocket.project.databinding.FragmentWalletBinding
import com.websocket.project.ui.base.convertDpToPixel
import com.websocket.project.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : Fragment(),
    WalletActionsListener,
    WalletListener {

    private val viewModel: WalletViewModel by viewModels()
    private lateinit var binding: FragmentWalletBinding

    private val walletActionsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        WalletActionsAdapter(this)
    }

    private val fragmentWalletPositions by lazy(LazyThreadSafetyMode.NONE) {
        FragmentWalletPositions.newInstance()
    }

    private val fragmentWalletPositionHistory by lazy(LazyThreadSafetyMode.NONE) {
        FragmentWalletPositionHistory.newInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWalletBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWalletActionsRecycler()
        initWalletTradingAccountTabLayout()

        with(binding) {
            walletListenerBinding = this@WalletFragment
            walletHeaderLayoutInclude.walletViewModelBinding = viewModel
            walletWalletLayoutInclude.walletViewModelBinding = viewModel
            walletWalletTradingAccountInclude.walletViewModelBinding = viewModel
            walletHeaderLayoutInclude.walletListenerBinding = this@WalletFragment
        }
    }

    private fun initWalletTradingAccountTabLayout() {

        val adapter = WalletTabFragmentAdapter(
            (activity as MainActivity).supportFragmentManager,
            this.lifecycle
        )
        var indicatorWidth = 0

        with(binding.walletWalletTradingAccountInclude) {

            walletTradingAccountViewPager.adapter = adapter

            adapter.addFragment(fragmentWalletPositions)
            adapter.addFragment(fragmentWalletPositionHistory)

            TabLayoutMediator(
                binding.walletWalletTradingAccountInclude.walletTradingAccountTabLayout,
                binding.walletWalletTradingAccountInclude.walletTradingAccountViewPager
            ) { tab, position ->
                binding.walletWalletTradingAccountInclude.walletTradingAccountViewPager.setCurrentItem(
                    tab.position,
                    true
                )

                if (position == 0) tab.text = getString(R.string.wallet_positions)
                if (position == 1) tab.text = getString(R.string.wallet_position_history)
            }.attach()
            walletTradingAccountTabLayout.post {
                indicatorWidth = walletTradingAccountTabLayout.width / 2

                val indicatorParams =
                    walletTradingAccountIndicatorView.layoutParams as FrameLayout.LayoutParams
                indicatorParams.width = indicatorWidth
                walletTradingAccountIndicatorView.layoutParams = indicatorParams
            }


            walletTradingAccountViewPager.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageScrolled(
                        i: Int,
                        positionOffset: Float,
                        positionOffsetPx: Int
                    ) {
                        val params =
                            walletTradingAccountIndicatorView.layoutParams as FrameLayout.LayoutParams

                        val translationOffset: Float = (positionOffset + i) * indicatorWidth
                        params.leftMargin =
                            translationOffset.toInt() + convertDpToPixel(16f, requireContext())
                        params.topMargin = convertDpToPixel(12f, requireContext())
                        walletTradingAccountIndicatorView.layoutParams = params
                    }

                    override fun onPageSelected(i: Int) {}
                    override fun onPageScrollStateChanged(i: Int) {}
                })
        }
    }

    private fun initWalletActionsRecycler() {
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        layoutManager.alignItems = AlignItems.CENTER
        binding.walletActionsRecycler.layoutManager = layoutManager
        binding.walletActionsRecycler.adapter = walletActionsAdapter
    }

    override fun onWalletActionClick(walletAction: WalletAction) {
        when(walletAction) {
            WalletAction.DEPOSIT -> {}
            WalletAction.BUY -> {}
            WalletAction.WITHDRAW -> {}
            WalletAction.SELL -> {}
        }
    }

    override fun showBalanceClick() {
        val isBalanceShownOnClick = !viewModel.showBalance.get()
        viewModel.showBalance.set(isBalanceShownOnClick)
        if (isBalanceShownOnClick) {
            binding.walletHeaderLayoutInclude.walletHeaderHideBalanceBtn.setImageResource(R.drawable.ic_visibility_show)
        } else {
            binding.walletHeaderLayoutInclude.walletHeaderHideBalanceBtn.setImageResource(R.drawable.ic_visibility_hide)
        }
        fragmentWalletPositions.showBalance(isBalanceShownOnClick)
        fragmentWalletPositionHistory.showBalance(isBalanceShownOnClick)
    }

    override fun transactionHistoryBtnClick() {
        //Go to transaction history
        Log.e("TAG", "transactionHistoryBtnClick")
    }

    companion object {
        fun newInstance() = WalletFragment()
    }
}