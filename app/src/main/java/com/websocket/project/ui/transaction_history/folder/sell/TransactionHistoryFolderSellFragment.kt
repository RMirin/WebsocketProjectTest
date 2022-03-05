package com.websocket.project.ui.transaction_history.folder.sell

import android.os.Bundle
import android.view.View
import com.websocket.project.databinding.FragmentTransactionHistoryFolderSellBinding
import com.websocket.project.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFolderSellFragment : BaseFragment<FragmentTransactionHistoryFolderSellBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViewBinding(): FragmentTransactionHistoryFolderSellBinding =
        FragmentTransactionHistoryFolderSellBinding.inflate(layoutInflater)

    companion object {
        fun newInstance() = TransactionHistoryFolderSellFragment()
    }
}