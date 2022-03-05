package com.websocket.project.ui.transaction_history.folder.withdrawal

import android.os.Bundle
import android.view.View
import com.websocket.project.databinding.FragmentTransactionHistoryFolderWithdrawalBinding
import com.websocket.project.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFolderWithdrawalFragment : BaseFragment<FragmentTransactionHistoryFolderWithdrawalBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViewBinding(): FragmentTransactionHistoryFolderWithdrawalBinding =
        FragmentTransactionHistoryFolderWithdrawalBinding.inflate(layoutInflater)

    companion object {
        fun newInstance() = TransactionHistoryFolderWithdrawalFragment()
    }
}