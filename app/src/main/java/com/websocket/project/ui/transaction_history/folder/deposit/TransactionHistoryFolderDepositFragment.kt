package com.websocket.project.ui.transaction_history.folder.deposit

import android.os.Bundle
import android.view.View
import com.websocket.project.databinding.FragmentTransactionHistoryFolderDepositBinding
import com.websocket.project.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFolderDepositFragment : BaseFragment<FragmentTransactionHistoryFolderDepositBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViewBinding(): FragmentTransactionHistoryFolderDepositBinding =
        FragmentTransactionHistoryFolderDepositBinding.inflate(layoutInflater)

    companion object {
        fun newInstance() = TransactionHistoryFolderDepositFragment()
    }
}