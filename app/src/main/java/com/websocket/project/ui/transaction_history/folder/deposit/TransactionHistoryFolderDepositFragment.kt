package com.websocket.project.ui.transaction_history.folder.deposit

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.databinding.FragmentTransactionHistoryFolderDepositBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.transaction_history.TransactionHistoryModel
import com.websocket.project.ui.transaction_history.folder.buy.TransactionHistoryFolderBuyActionListener
import com.websocket.project.ui.transaction_history.folder.buy.TransactionHistoryFolderBuyAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFolderDepositFragment :
    BaseFragment<FragmentTransactionHistoryFolderDepositBinding>(),
    TransactionHistoryFolderDepositActionListener {

    private val transactionHistoryFolderDepositAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TransactionHistoryFolderDepositAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.showContent = false
        initRecycler()
    }

    override fun initViewBinding(): FragmentTransactionHistoryFolderDepositBinding =
        FragmentTransactionHistoryFolderDepositBinding.inflate(layoutInflater)

    private fun initRecycler() {
        binding.transactionHistoryFolderDepositRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.transactionHistoryFolderDepositRecycler.adapter = transactionHistoryFolderDepositAdapter
    }

    override fun onTransactionHistoryFolderDepositItemClick(transactionHistoryModel: TransactionHistoryModel) {
    }

    companion object {
        fun newInstance() = TransactionHistoryFolderDepositFragment()
    }
}