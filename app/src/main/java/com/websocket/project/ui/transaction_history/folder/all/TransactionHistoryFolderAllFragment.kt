package com.websocket.project.ui.transaction_history.folder.all

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.databinding.FragmentTransactionHistoryFolderAllBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.transaction_history.TransactionHistoryModel
import com.websocket.project.ui.transaction_history.TransactionStatus
import com.websocket.project.ui.transaction_history.TransactionType
import com.websocket.project.ui.transaction_history.detail.DialogFragmentTransactionHistoryDetail
import com.websocket.project.ui.transaction_history.detail.DialogFragmentTransactionHistoryDetail.Companion.TRANSACTION_HISTORY_DETAIL_KEY_DIALOG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFolderAllFragment :
    BaseFragment<FragmentTransactionHistoryFolderAllBinding>(),
    TransactionHistoryFolderAllActionListener {

    private val transactionHistoryFolderAllAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TransactionHistoryFolderAllAdapter(this)
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
                fee = 3.30f,
                address = "0x81afvc-g355-6m23-bb08-vbfg3ebe0b11",
                txHash = "0x91zfh54-a450-0r98-gg99-qwh5yc10k19",
                transactionId = "c34f6e71-b823-4b43-bb08-edbfa3c572c2"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.WITHDRAWAL,
                status = TransactionStatus.CLOSED,
                amount = -20.00f,
                fee = 3.30f,
                address = "0x81afvc-g355-6m23-bb08-vbfg3ebe0b11",
                txHash = "0x91zfh54-a450-0r98-gg99-qwh5yc10k19",
                transactionId = "c34f6e71-b823-4b43-bb08-edbfa3c572c2"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.BUY,
                status = TransactionStatus.CLOSED,
                amount = 10.00f,
                fee = 0f,
                address = "0x81afvc-g355-6m23-bb08-vbfg3ebe0b11",
                txHash = "0x91zfh54-a450-0r98-gg99-qwh5yc10k19",
                transactionId = "c34f6e71-b823-4b43-bb08-edbfa3c572c2"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.DEPOSIT,
                status = TransactionStatus.CLOSED,
                amount = 5.00f,
                fee = 0f,
                address = "0x81afvc-g355-6m23-bb08-vbfg3ebe0b11",
                txHash = "0x91zfh54-a450-0r98-gg99-qwh5yc10k19",
                transactionId = "c34f6e71-b823-4b43-bb08-edbfa3c572c2"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.WITHDRAWAL,
                status = TransactionStatus.CLOSED,
                amount = -5000000.00f,
                fee = 3.30f,
                address = "0x81afvc-g355-6m23-bb08-vbfg3ebe0b11",
                txHash = "0x91zfh54-a450-0r98-gg99-qwh5yc10k19",
                transactionId = "c34f6e71-b823-4b43-bb08-edbfa3c572c2"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.WITHDRAWAL,
                status = TransactionStatus.CLOSED,
                amount = -20.00f,
                fee = 3.30f,
                address = "0x81afvc-g355-6m23-bb08-vbfg3ebe0b11",
                txHash = "0x91zfh54-a450-0r98-gg99-qwh5yc10k19",
                transactionId = "c34f6e71-b823-4b43-bb08-edbfa3c572c2"
            ),
            TransactionHistoryModel(
                name = "USDT",
                nameFull = "Tether",
                date = time,
                time = time,
                type = TransactionType.BUY,
                status = TransactionStatus.CLOSED,
                amount = 10.00f,
                fee = 0f,
                address = "0x81afvc-g355-6m23-bb08-vbfg3ebe0b11",
                txHash = "0x91zfh54-a450-0r98-gg99-qwh5yc10k19",
                transactionId = "c34f6e71-b823-4b43-bb08-edbfa3c572c2"
            )
        )
        transactionHistoryFolderAllAdapter.setData(dataList)
    }

    override fun initViewBinding(): FragmentTransactionHistoryFolderAllBinding =
        FragmentTransactionHistoryFolderAllBinding.inflate(layoutInflater)

    override fun onTransactionHistoryFolderAllItemClick(transactionHistoryModel: TransactionHistoryModel) {
        DialogFragmentTransactionHistoryDetail(
            transactionHistoryModel
        ).show(
            (activity as MainActivity).supportFragmentManager, TRANSACTION_HISTORY_DETAIL_KEY_DIALOG
        )
    }

    companion object {
        fun newInstance() = TransactionHistoryFolderAllFragment()
    }
}