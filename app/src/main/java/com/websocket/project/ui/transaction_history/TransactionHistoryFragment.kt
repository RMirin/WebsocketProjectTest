package com.websocket.project.ui.transaction_history

import android.os.Bundle
import android.view.View
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.websocket.project.databinding.FragmentTransactionHistoryBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFragment : BaseFragment<FragmentTransactionHistoryBinding>(),
    TransactionHistoryFolderListener, TransactionHistoryFragmentListener {

    private val transactionHistoryFolderAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TransactionHistoryFolderAdapter(this, 0)
    }

    override fun initViewBinding(): FragmentTransactionHistoryBinding =
        FragmentTransactionHistoryBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initFolderRecycler()
        with(binding) {
            fragmentTransactionHistoryListener = this@TransactionHistoryFragment
        }
    }

    private fun initFolderRecycler() {
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        layoutManager.alignItems = AlignItems.CENTER
        binding.transactionHistoryFoldersRecycler.layoutManager = layoutManager
        binding.transactionHistoryFoldersRecycler.adapter = transactionHistoryFolderAdapter
    }

    override fun onTransactionHistoryFolderSelected(transactionHistoryFolder: TransactionHistoryFolder) {
        val transactionHistoryFolderPosition = TransactionHistoryFolder.valueOf(transactionHistoryFolder.name).ordinal
        transactionHistoryFolderAdapter.onTransactionHistoryFolderSelected(transactionHistoryFolderPosition)
    }

    companion object {
        fun newInstance() = TransactionHistoryFragment()
    }

    override fun backBtnClick() {
        (activity as MainActivity).onBackPressed()
    }
}