package com.websocket.project.ui.transaction_history.folder.withdrawal

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.databinding.FragmentTransactionHistoryFolderWithdrawalBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.transaction_history.TransactionHistoryModel
import com.websocket.project.ui.transaction_history.folder.buy.TransactionHistoryFolderBuyActionListener
import com.websocket.project.ui.transaction_history.folder.buy.TransactionHistoryFolderBuyAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFolderWithdrawalFragment :
    BaseFragment<FragmentTransactionHistoryFolderWithdrawalBinding>(),
    TransactionHistoryFolderWithdrawalActionListener {

    private val transactionHistoryFolderWithdrawalAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TransactionHistoryFolderWithdrawalAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.showContent = false
        initRecycler()
    }

    override fun initViewBinding(): FragmentTransactionHistoryFolderWithdrawalBinding =
        FragmentTransactionHistoryFolderWithdrawalBinding.inflate(layoutInflater)

    private fun initRecycler() {
        binding.transactionHistoryFolderWithdrawalRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.transactionHistoryFolderWithdrawalRecycler.adapter = transactionHistoryFolderWithdrawalAdapter
    }

    override fun onTransactionHistoryFolderWithdrawalItemClick(transactionHistoryModel: TransactionHistoryModel) {

    }

    companion object {
        fun newInstance() = TransactionHistoryFolderWithdrawalFragment()
    }
}