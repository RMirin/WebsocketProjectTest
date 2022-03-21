package com.websocket.project.ui.deposit

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.websocket.project.R
import com.websocket.project.databinding.FragmentDepositBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.base.getBitmapFromView
import com.websocket.project.ui.base.getLocalizedResources
import com.websocket.project.ui.base.observe
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.network.Network
import com.websocket.project.ui.network.NetworkBottomSheetFragment
import java.io.File
import java.io.FileOutputStream
import java.util.*

class DepositFragment: BaseFragment<FragmentDepositBinding>(), DepositFragmentActionListener {

    private val viewModel: DepositViewModel by viewModels()

    private val clipboard by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity).getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(
            NetworkBottomSheetFragment.NETWORK_RESULT_KEY
        )?.observe(viewLifecycleOwner) { result ->
            viewModel.setSelectedNetwork(result)
        }

        val localizedResources = getLocalizedResources(requireContext(), Locale("ru"))

        with(binding) {
            depositTopbarLayoutInclude.backClick = View.OnClickListener {
                (activity as MainActivity).onBackPressed()
            }

            depositTopbarLayoutInclude.title.text =
                localizedResources?.getString(R.string.deposit_title)
            depositNetworkTitleText.text =
                localizedResources?.getString(R.string.deposit_network_title)
            depositNetworkBtn.text =
                localizedResources?.getString(R.string.deposit_network_btn_hint)
            depositUsdtAddressTitleText.text =
                localizedResources?.getString(R.string.deposit_usdt_address_title)
            depositSaveImageBtn.text = localizedResources?.getString(R.string.deposit_save_image)

            depositViewModelBinding = viewModel
            depositFragmentActionListenerBinding = this@DepositFragment
        }

        observeLiveData()
    }

    override fun initViewBinding(): FragmentDepositBinding =
        FragmentDepositBinding.inflate(layoutInflater)

    override fun onDepositNetworkBtnClicked() {
        findNavController().navigate(DepositFragmentDirections.actionDepositFragmentToWithdrawNetworkBottomSheetFragment(Network.values()))
    }

    override fun onCopyAddressClick() {
        val clip = ClipData.newPlainText("", binding.depositUsdtAddressText.text)
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(activity as MainActivity, R.string.deposit_address_copied, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveImageClick() {
        requestPermissions(
            PERMISSIONS,
            REQUEST_CODE_SCREENSHOT
        )
        takeScreenshot()
    }

    override fun onShareAddressClick() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, binding.depositUsdtAddressText.text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            when (requestCode) {
                REQUEST_CODE_SCREENSHOT -> {
                    takeScreenshot()
                }
            }
        }
    }

    private fun takeScreenshot() {
        val date = Date()
        DateFormat.format(SCREENSHOT_NAME_DATE_FORMAT, date)
        val currentDate = date.toString().replace(":", ".")
        try {
            val mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + currentDate + ".jpg"

            val viewForBitmap: View? = activity?.window?.decorView?.rootView
            val bitmapFromView = viewForBitmap?.let { getBitmapFromView(it) }
            val imageFile = File(mPath)
            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmapFromView?.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

            Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
                val f = File(mPath)
                mediaScanIntent.data = Uri.fromFile(f)
                activity?.sendBroadcast(mediaScanIntent)
            }
            findNavController().navigate(DepositFragmentDirections.actionDepositFragmentToDialogFragmentAlert(R.string.deposit_image_saved_msg))
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun observeLiveData() {
        val localizedResources = getLocalizedResources(requireContext(), Locale("ru"))
        with(viewModel) {
            observe(networkChosenPosition) { networkChosenPosition ->
                if (networkChosenPosition != -1) {
                    val withdrawNetwork = Network.values()[networkChosenPosition]
                    val name = requireContext().getString(withdrawNetwork.networkName)
                    val code = requireContext().getString(withdrawNetwork.networkCode)
                    with(binding) {
                        depositNetworkBtn.text = localizedResources?.getString(R.string.deposit_network_btn, name, code)
                        depositNetworkBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        depositAttentionText.text =  localizedResources?.getString(R.string.deposit_attention_msg, code, name)
                        depositArriveText.text = localizedResources?.getString(R.string.deposit_arrive_msg, code)
                    }
                }
            }

            observe(depositFee) { depositFee ->
                binding.depositFeeText.text = localizedResources?.getString(R.string.deposit_fee_msg, depositFee)
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_SCREENSHOT = 111

        private val PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        private const val SCREENSHOT_NAME_DATE_FORMAT = "yyyy-MM-dd_hh:mm:ss"
    }
}