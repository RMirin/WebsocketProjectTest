package com.websocket.project.ui.transaction_history.folder.sell

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.databinding.FragmentTransactionHistoryFolderSellBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.transaction_history.TransactionHistoryModel
import com.websocket.project.ui.transaction_history.folder.buy.TransactionHistoryFolderBuyActionListener
import com.websocket.project.ui.transaction_history.folder.buy.TransactionHistoryFolderBuyAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFolderSellFragment :
    BaseFragment<FragmentTransactionHistoryFolderSellBinding>(),
    TransactionHistoryFolderSellActionListener {

    private val transactionHistoryFolderSellAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TransactionHistoryFolderSellAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.showContent = false
        initRecycler()
    }

    override fun initViewBinding(): FragmentTransactionHistoryFolderSellBinding =
        FragmentTransactionHistoryFolderSellBinding.inflate(layoutInflater)

    private fun initRecycler() {
        binding.transactionHistoryFolderSellRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.transactionHistoryFolderSellRecycler.adapter = transactionHistoryFolderSellAdapter
    }

    override fun onTransactionHistoryFolderSellItemClick(transactionHistoryModel: TransactionHistoryModel) {

    }

    companion object {
        fun newInstance() = TransactionHistoryFolderSellFragment()
    }
}