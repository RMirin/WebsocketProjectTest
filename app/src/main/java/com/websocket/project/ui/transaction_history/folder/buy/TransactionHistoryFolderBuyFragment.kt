package com.websocket.project.ui.transaction_history.folder.buy

import android.os.Bundle
import android.view.View
import com.websocket.project.databinding.FragmentTransactionHistoryFolderBuyBinding
import com.websocket.project.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFolderBuyFragment : BaseFragment<FragmentTransactionHistoryFolderBuyBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.showContent = false
    }

    override fun initViewBinding(): FragmentTransactionHistoryFolderBuyBinding =
        FragmentTransactionHistoryFolderBuyBinding.inflate(layoutInflater)

    companion object {
        fun newInstance() = TransactionHistoryFolderBuyFragment()
    }
}