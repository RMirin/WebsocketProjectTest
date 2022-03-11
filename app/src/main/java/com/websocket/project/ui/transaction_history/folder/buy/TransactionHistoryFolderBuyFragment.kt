package com.websocket.project.ui.transaction_history.folder.buy

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.databinding.FragmentTransactionHistoryFolderBuyBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.transaction_history.TransactionHistoryModel
import com.websocket.project.ui.transaction_history.folder.all.TransactionHistoryFolderAllAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFolderBuyFragment :
    BaseFragment<FragmentTransactionHistoryFolderBuyBinding>(),
    TransactionHistoryFolderBuyActionListener {

    private val transactionHistoryFolderBuyAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TransactionHistoryFolderBuyAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.showContent = false
        initRecycler()
    }

    override fun initViewBinding(): FragmentTransactionHistoryFolderBuyBinding =
        FragmentTransactionHistoryFolderBuyBinding.inflate(layoutInflater)

    private fun initRecycler() {
        binding.transactionHistoryFolderBuyRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.transactionHistoryFolderBuyRecycler.adapter = transactionHistoryFolderBuyAdapter
    }

    override fun onTransactionHistoryFolderBuyItemClick(transactionHistoryModel: TransactionHistoryModel) {

    }

    companion object {
        fun newInstance() = TransactionHistoryFolderBuyFragment()
    }
}