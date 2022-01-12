package com.websocket.project.ui.main

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.websocket.project.databinding.FragmentCryptoPairBinding
import com.websocket.project.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.ui.base.launchWhenStarted
import java.lang.Exception

@AndroidEntryPoint
class CryptoPairFragment : BaseFragment<FragmentCryptoPairBinding>(), CryptoRecyclerOnClick {

    private var isPermissionGranted = false

    private val viewModel: MainActivityViewModel by viewModels()

    private val cryptoPairAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CryptoPairAdapter(this)
    }

    override fun initViewBinding(): FragmentCryptoPairBinding =
        FragmentCryptoPairBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dividerItemDecoration = DividerItemDecoration(
            activity,
            LinearLayoutManager.VERTICAL
        )
        binding.mainRecycler.addItemDecoration(dividerItemDecoration)
        binding.mainRecycler.adapter = cryptoPairAdapter

        viewModel.ticker.observe(viewLifecycleOwner, { ticker ->
            binding.cryptoPairProgress.visibility = View.GONE
            cryptoPairAdapter.setNewCryptoHashMap(ticker)
            Log.d("TAG", "onCreate: $ticker")
        })

        viewModel.permissionState.launchWhenStarted(lifecycleScope) { isPermissionGranted ->
            this.isPermissionGranted = isPermissionGranted
        }

        binding.pairAttachBtn.setOnClickListener {
            onAddClick()
        }
    }

    private fun onAddClick() {
        if (isPermissionGranted) {
            openFileChooser()
        } else {
            checkSelfPermissions()
        }
    }

    private fun checkSelfPermissions() {
        requestPermissions(
            PERMISSIONS,
            MY_PERMISSIONS_REQUEST
        )
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
            //Set flag in shared prefs to true
//            policyViewModel.grantPermission()
            openFileChooser()
        }
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        val mimeTypes = arrayOf("application/pdf", "image/jpg", "image/png", "image/pdf")
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, FILE_CHOOSER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_CHOOSER_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            val uri: Uri? = data?.data
            var dataSize = 0
            Log.e("TAG", "onActivityResult: $uri")
            val scheme: String? = uri?.scheme
            if (scheme.equals(ContentResolver.SCHEME_CONTENT) && uri != null) {
                try {
                    val fileInputStream = activity?.contentResolver?.openInputStream(uri)
                    dataSize = fileInputStream?.available() ?: 0
                    if (dataSize < UPLOAD_FILE_MAX_SIZE_IN_BYTES) {
                        Toast.makeText(activity, "File size in bytes: $dataSize", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(activity, "File is too large", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                Log.e("TAG", "File size in bytes: $dataSize")
            }
        }
    }

    override fun goToCandleScreen(pairName: String) {
        findNavController().navigate(
            CryptoPairFragmentDirections.actionCryptoPairFragmentToCandleFragment(
                pairName
            )
        )
    }

    companion object {
        fun newInstance() = CryptoPairFragment()

        private val PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        private const val MY_PERMISSIONS_REQUEST = 0
        private const val FILE_CHOOSER_REQUEST_CODE = 111

        //10 Mb
        private const val UPLOAD_FILE_MAX_SIZE_IN_BYTES = 10_000_000
    }
}