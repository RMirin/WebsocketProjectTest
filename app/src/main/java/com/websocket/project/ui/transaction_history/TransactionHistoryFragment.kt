package com.websocket.project.ui.transaction_history

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.databinding.FragmentTransactionHistoryBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.base.TabFragmentAdapter
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.transaction_history.folder.*
import com.websocket.project.ui.transaction_history.folder.all.TransactionHistoryFolderAllFragment
import com.websocket.project.ui.transaction_history.folder.buy.TransactionHistoryFolderBuyFragment
import com.websocket.project.ui.transaction_history.folder.deposit.TransactionHistoryFolderDepositFragment
import com.websocket.project.ui.transaction_history.folder.sell.TransactionHistoryFolderSellFragment
import com.websocket.project.ui.transaction_history.folder.withdrawal.TransactionHistoryFolderWithdrawalFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFragment : BaseFragment<FragmentTransactionHistoryBinding>(),
    TransactionHistoryFolderListener, TransactionHistoryFragmentListener {

    private val transactionHistoryFolderAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TransactionHistoryFolderAdapter(this, 0)
    }

    private val fragmentTransactionHistoryFolderAll by lazy(LazyThreadSafetyMode.NONE) {
        TransactionHistoryFolderAllFragment.newInstance()
    }

    private val fragmentTransactionHistoryFolderDepositFragment by lazy(LazyThreadSafetyMode.NONE) {
        TransactionHistoryFolderDepositFragment.newInstance()
    }

    private val fragmentTransactionHistoryFolderWithdrawalFragment by lazy(LazyThreadSafetyMode.NONE) {
        TransactionHistoryFolderWithdrawalFragment.newInstance()
    }

    private val fragmentTransactionHistoryFolderBuyFragment by lazy(LazyThreadSafetyMode.NONE) {
        TransactionHistoryFolderBuyFragment.newInstance()
    }

    private val fragmentTransactionHistoryFolderSellFragment by lazy(LazyThreadSafetyMode.NONE) {
        TransactionHistoryFolderSellFragment.newInstance()
    }

    override fun initViewBinding(): FragmentTransactionHistoryBinding =
        FragmentTransactionHistoryBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initFolders()
        with(binding) {
            fragmentTransactionHistoryListener = this@TransactionHistoryFragment
        }
    }

    private fun initFolders() {
        val adapter = TabFragmentAdapter(
            (activity as MainActivity).supportFragmentManager,
            this.lifecycle
        )

        with(binding) {
            transactionHistoryRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            transactionHistoryRecycler.adapter = transactionHistoryFolderAdapter

            transactionHistoryViewPager.offscreenPageLimit = 5
            transactionHistoryViewPager.adapter = adapter
            transactionHistoryViewPager.isUserInputEnabled = false
        }

        with(adapter) {
            addFragment(fragmentTransactionHistoryFolderAll)
            addFragment(fragmentTransactionHistoryFolderDepositFragment)
            addFragment(fragmentTransactionHistoryFolderWithdrawalFragment)
            addFragment(fragmentTransactionHistoryFolderBuyFragment)
            addFragment(fragmentTransactionHistoryFolderSellFragment)
        }
    }

    override fun onTransactionHistoryFolderSelected(transactionHistoryFolder: TransactionHistoryFolder) {
        val transactionHistoryFolderPosition = TransactionHistoryFolder.valueOf(transactionHistoryFolder.name).ordinal
        binding.transactionHistoryViewPager.currentItem = transactionHistoryFolderPosition
        transactionHistoryFolderAdapter.onTransactionHistoryFolderSelected(transactionHistoryFolderPosition)
    }

    override fun backBtnClick() {
        (activity as MainActivity).onBackPressed()
    }

    companion object {
        fun newInstance() = TransactionHistoryFragment()
    }
}