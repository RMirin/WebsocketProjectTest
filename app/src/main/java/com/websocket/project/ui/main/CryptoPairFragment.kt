package com.websocket.project.ui.main

import android.Manifest.permission
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
import com.websocket.project.databinding.FragmentCryptoPairBinding
import com.websocket.project.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.ui.base.launchWhenStarted
import com.websocket.project.ui.main.attach_file.AttachFileAction
import com.websocket.project.ui.main.attach_file.AttachFileBottomSheetFragment
import com.websocket.project.ui.main.attach_file.AttachFileBottomSheetListener
import java.lang.Exception
import android.content.Context
import androidx.core.app.ActivityCompat
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.provider.Settings
import androidx.core.content.ContextCompat

@AndroidEntryPoint
class CryptoPairFragment: BaseFragment<FragmentCryptoPairBinding>(), AttachFileBottomSheetListener {

    private var isPermissionGranted = false

    private val viewModel: MainActivityViewModel by viewModels()

    private val cryptoPairAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CryptoPairAdapter()
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
            Log.e("TAG", "onCreate: $ticker")
        })

        viewModel.permissionState.launchWhenStarted(lifecycleScope) { isPermissionGranted ->
            this.isPermissionGranted = isPermissionGranted
        }

        binding.pairAttachBtn.setOnClickListener {
            AttachFileBottomSheetFragment(this@CryptoPairFragment).show(
                (activity as MainActivity).supportFragmentManager,
                "tag"
            )
        }
    }

    private fun onCameraClick() {
        checkCameraPermissions()
    }

    private fun onDocumentClick() {
        ContextCompat.checkSelfPermission(requireContext(), permission.READ_EXTERNAL_STORAGE)
        if (isPermissionGranted) {
            openFileChooser()
        } else {
            checkSelfPermissions()
        }
    }

    private fun onGalleryClick() {
        if (isPermissionGranted) {
            openGallery()
        } else {
            checkSelfPermissions()
        }
    }

    private fun checkSelfPermissions() {
        requestPermissions(PERMISSIONS,
            MY_PERMISSIONS_REQUEST)
    }

    private fun checkCameraPermissions() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(
                    String.format(
                        "package:%s",
                        getApplicationContext<Context>().packageName
                    )
                )
                startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE)
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(
                activity as MainActivity,
                PERMISSIONS,
                MY_PERMISSIONS_REQUEST
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              //Set flag in shared prefs to true
//            policyViewModel.grantPermission()
            openFileChooser()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
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
        Log.e("TAG", "onActivityResult: ", )
        if (resultCode == AppCompatActivity.RESULT_OK) {
            when(requestCode) {
                FILE_CHOOSER_REQUEST_CODE -> {
                    val uri: Uri? = data?.data
                    var dataSize = 0
                    Log.e("TAG", "FILE_CHOOSER_REQUEST_CODE onActivityResult: $uri")
                    val scheme: String? = uri?.scheme
                    if (scheme.equals(ContentResolver.SCHEME_CONTENT) && uri != null) {
                        try {
                            val fileInputStream = activity?.contentResolver?.openInputStream(uri)
                            dataSize = fileInputStream?.available() ?: 0
                            if (dataSize < UPLOAD_FILE_MAX_SIZE_IN_BYTES) {
                                Toast.makeText(
                                    activity,
                                    "File size in bytes: $dataSize",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(activity, "File is too large", Toast.LENGTH_LONG)
                                    .show()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        Log.e("TAG", "File size in bytes: $dataSize")
                    }
                }
                GALLERY_REQUEST_CODE -> {
                    val uri: Uri? = data?.data
                    Log.e("TAG", "GALLERY_REQUEST_CODE onActivityResult: $uri")
                }
                TAKE_PHOTO_REQUEST_CODE -> {
                    Log.e("TAG", "TAKE_PHOTO_REQUEST_CODE onActivityResult: ${data?.data}")
                }
            }
        }
    }

    override fun onFilterClick(action: AttachFileAction) {
        when(action) {
            AttachFileAction.CAMERA -> {
                onCameraClick()
            }
            AttachFileAction.DOCUMENT -> {
                onDocumentClick()
            }
            AttachFileAction.GALLERY -> {
                onGalleryClick()
            }
        }
    }

    companion object {
        fun newInstance() = CryptoPairFragment()

        private val PERMISSIONS = arrayOf(
            permission.READ_EXTERNAL_STORAGE,
            permission.WRITE_EXTERNAL_STORAGE
        )

        private const val MY_PERMISSIONS_REQUEST = 0
        private const val FILE_CHOOSER_REQUEST_CODE = 111
        private const val GALLERY_REQUEST_CODE = 222
        private const val TAKE_PHOTO_REQUEST_CODE = 333
        //10 Mb
        private const val UPLOAD_FILE_MAX_SIZE_IN_BYTES = 10_000_000
    }
}