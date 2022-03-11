package com.websocket.project.ui.transaction_history.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.websocket.project.R
import com.websocket.project.databinding.DialogFragmentTransactionHistoryDetailBinding
import com.websocket.project.ui.base.convertDpToPixel
import com.websocket.project.ui.base.observe
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.transaction_history.TransactionHistoryModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogFragmentTransactionHistoryDetail(
    val transactionHistoryModel: TransactionHistoryModel,
) : DialogFragment(), DialogFragmentTransactionHistoryDetailListener {

    private val viewModel: TransactionHistoryDetailViewModel by viewModels()
    lateinit var binding: DialogFragmentTransactionHistoryDetailBinding
    private val clipboard by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity).getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        val param: WindowManager.LayoutParams? = dialog?.window?.attributes
        param?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        param?.width = ViewGroup.LayoutParams.MATCH_PARENT
        val back = ColorDrawable(Color.TRANSPARENT)
        val insetFloat = convertDpToPixel(16f, activity as MainActivity)
        val inset = InsetDrawable(back, insetFloat)

        dialog?.window?.setBackgroundDrawable(inset)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogFragmentTransactionHistoryDetailBinding.bind(inflater.inflate(R.layout.dialog_fragment_transaction_history_detail, container))

        with(binding) {
            transactionHistoryModelBinding = transactionHistoryModel
            dialogFragmentTransactionHistoryDetailListenerBinding = this@DialogFragmentTransactionHistoryDetail
        }

        observeLiveData()

        return binding.root
    }

    private fun observeLiveData() {
        with(viewModel) {
            observe(addressShown) { isAddressShown ->
                binding.addressShownBinding = isAddressShown
            }

            observe(txHashShown) { isTxHashShown ->
                binding.txHashShownBinding = isTxHashShown
            }

            observe(transactionIdShown) { isTransactionIdShown ->
                binding.transactionIdShownBinding = isTransactionIdShown
            }
        }
    }

    private fun showCopyToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onHideAddressClick() {
        viewModel.showHideAddress()
    }

    override fun onHideTxHashClick() {
        viewModel.showHideTxHash()
    }

    override fun onHideTransactionIdClick() {
        viewModel.showHideTransactionId()
    }

    override fun onCloseClick() {
        dismiss()
    }

    override fun onCopyAddressClick() {
        val clip = ClipData.newPlainText("", transactionHistoryModel.address)
        clipboard?.setPrimaryClip(clip)
        showCopyToast(
            requireContext().getString(
                R.string.transaction_history_detail_copied,
                requireContext().getString(R.string.transaction_history_detail_address)
            )
        )
    }

    override fun onCopyTxHashClick() {
        val clip = ClipData.newPlainText("", transactionHistoryModel.txHash)
        clipboard?.setPrimaryClip(clip)
        showCopyToast(
            requireContext().getString(
                R.string.transaction_history_detail_copied,
                requireContext().getString(R.string.transaction_history_detail_tx_hash)
            )
        )
    }

    override fun onCopyTransactionIdClick() {
        val clip = ClipData.newPlainText("", transactionHistoryModel.transactionId)
        clipboard?.setPrimaryClip(clip)
        showCopyToast(
            requireContext().getString(
                R.string.transaction_history_detail_copied,
                requireContext().getString(R.string.transaction_history_detail_transaction_id)
            )
        )
    }

    companion object {
        const val TRANSACTION_HISTORY_DETAIL_KEY_DIALOG = "DialogFragmentTransactionHistoryDetail"
    }
}