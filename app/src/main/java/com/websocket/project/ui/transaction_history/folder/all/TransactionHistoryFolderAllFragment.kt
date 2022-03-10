package com.websocket.project.ui.transaction_history.folder.all

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.databinding.FragmentTransactionHistoryFolderAllBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.transaction_history.TransactionHistoryModel
import com.websocket.project.ui.transaction_history.TransactionStatus
import com.websocket.project.ui.transaction_history.TransactionType
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TransactionHistoryFolderAllFragment : BaseFragment<FragmentTransactionHistoryFolderAllBinding>() {

    private val transactionHistoryFolderAllAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TransactionHistoryFolderAllAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //variable to show/hide recycler and empty state
        binding.showContent = true
        initRecycler()
    }

    private fun initRecycler() {
        val time = System.currentTimeMillis()

        binding.transactionHistoryFolderAllRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.transactionHistoryFolderAllRecycler.adapter = transactionHistoryFolderAllAdapter
        val dataList = mutableListOf(
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.WITHDRAWAL,
                status = TransactionStatus.CLOSED,
                amount = -5000000.00f,
                fee = "3.30f"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.WITHDRAWAL,
                status = TransactionStatus.CLOSED,
                amount = -20.00f,
                fee = "3.30f"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.BUY,
                status = TransactionStatus.CLOSED,
                amount = 10.00f,
                fee = "no fee"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.DEPOSIT,
                status = TransactionStatus.CLOSED,
                amount = 5.00f,
                fee = "no fee"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.WITHDRAWAL,
                status = TransactionStatus.CLOSED,
                amount = -5000000.00f,
                fee = "3.30f"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.WITHDRAWAL,
                status = TransactionStatus.CLOSED,
                amount = -20.00f,
                fee = "3.30f"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.BUY,
                status = TransactionStatus.CLOSED,
                amount = 10.00f,
                fee = "no fee"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.DEPOSIT,
                status = TransactionStatus.CLOSED,
                amount = 5.00f,
                fee = "no fee"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.DEPOSIT,
                status = TransactionStatus.CLOSED,
                amount = 5.00f,
                fee = "no fee"
            ))
        transactionHistoryFolderAllAdapter.setData(dataList)
    }

    override fun initViewBinding(): FragmentTransactionHistoryFolderAllBinding =
        FragmentTransactionHistoryFolderAllBinding.inflate(layoutInflater)

    companion object {
        fun newInstance() = TransactionHistoryFolderAllFragment()
    }
}