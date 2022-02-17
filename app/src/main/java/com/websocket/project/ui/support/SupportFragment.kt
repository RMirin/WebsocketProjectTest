package com.websocket.project.ui.support

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.websocket.project.R
import com.websocket.project.databinding.FragmentSupportBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.support.attach_file.AttachFileAction
import com.websocket.project.ui.support.attach_file.AttachFileBottomSheetFragment
import com.websocket.project.ui.support.attach_file.AttachFileBottomSheetListener
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SupportFragment: BaseFragment<FragmentSupportBinding>(), AttachFileBottomSheetListener {

    lateinit var currentPhotoPath: String

    private val supportFilesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SupportFilesAdapter()
    }

    private var attachedFilesSize = 0

    override fun initViewBinding(): FragmentSupportBinding =
        FragmentSupportBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.supportFilesRecycler.adapter = supportFilesAdapter

        binding.supportAttachBtn.setOnClickListener {
            AttachFileBottomSheetFragment(this@SupportFragment).show(
                (activity as MainActivity).supportFragmentManager,
                "tag"
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
            when (requestCode) {
                REQUEST_CODE_FILE_CHOOSER -> {
                    openFileChooser()
                }
                REQUEST_CODE_GALLERY -> {
                    openGallery()
                }
                REQUEST_CODE_TAKE_PHOTO -> {
                    openCamera()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("TAG", "onActivityResult: $resultCode")
        if (resultCode == AppCompatActivity.RESULT_OK) {
            Log.e("TAG", "onActivityResult: resiult ok data ${data?.data} request code $requestCode")
            when(requestCode) {
                REQUEST_CODE_FILE_CHOOSER -> {
                    val uri: Uri? = data?.data
                    Log.e("TAG", "FILE_CHOOSER_REQUEST_CODE onActivityResult: $uri")
                    val scheme: String? = uri?.scheme
                    if (scheme.equals(ContentResolver.SCHEME_CONTENT) && uri != null) {
                        val fileSize = getFileSize(uri)
                        if (attachedFilesSize + fileSize < UPLOAD_FILE_MAX_SIZE_IN_BYTES) {
                            supportFilesAdapter.addItem(FileModel(
                                url = uri,
                                size = fileSize
                            ))
                            attachedFilesSize =+ fileSize
                        } else {
                            Toast.makeText(requireContext(), "File is too large!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                REQUEST_CODE_GALLERY -> {
                    val uri: Uri? = data?.data
                    Log.e("TAG", "GALLERY_REQUEST_CODE onActivityResult: $uri")
                    if (uri != null) {
                        val fileSize = getFileSize(uri)
                        if (attachedFilesSize + fileSize < UPLOAD_FILE_MAX_SIZE_IN_BYTES) {
                            supportFilesAdapter.addItem(ImageModel(
                                imageBitmap = getPhotoBitmap(uri),
                                size = fileSize
                            ))
                            attachedFilesSize =+ fileSize
                        } else {
                            Toast.makeText(requireContext(), "File is too large!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                REQUEST_CODE_TAKE_PHOTO -> {
                    saveImageFileToGallery()
                    if (currentPhotoPath.isNotEmpty()) {
                        val photoFile = File(currentPhotoPath)
                        val photoUri = Uri.fromFile(photoFile)
                        val fileSize = getFileSize(photoUri)
                        if (attachedFilesSize + fileSize < UPLOAD_FILE_MAX_SIZE_IN_BYTES) {
                            supportFilesAdapter.addItem(
                                ImageModel(
                                    imageBitmap = getPhotoBitmap(photoUri),
                                    size = fileSize
                                )
                            )
                            attachedFilesSize =+ fileSize
                        } else {
                            Toast.makeText(requireContext(), "File is too large!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    Log.e("TAG", "TAKE_PHOTO_REQUEST_CODE onActivityResult: $currentPhotoPath")
                }
            }
        } else if (resultCode == AppCompatActivity.RESULT_CANCELED && requestCode == REQUEST_CODE_TAKE_PHOTO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                    openCamera()
                } else {
                    Log.e("TAG", "REQUEST_CODE_TAKE_PHOTO onActivityResult: require access")
                    Toast.makeText(requireContext(), "Allow permission for storage access!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onCameraClick() {
        checkCameraPermissions()
    }

    private fun onDocumentClick() {
        checkSelfPermissions(
            PERMISSIONS,
            REQUEST_CODE_FILE_CHOOSER
        )
    }

    private fun onGalleryClick() {
        checkSelfPermissions(
            PERMISSIONS,
            REQUEST_CODE_GALLERY
        )
    }

    private fun checkSelfPermissions(permissions: Array<String>, permissionsRequestCode: Int) {
        requestPermissions(permissions,
            permissionsRequestCode)
    }

    private fun checkCameraPermissions() {
        if (activity?.packageManager?.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY) == true) {
//            if (SDK_INT >= Build.VERSION_CODES.R) {
//                try {
//                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
//                    intent.addCategory("android.intent.category.DEFAULT")
//                    intent.data = Uri.parse(
//                        String.format(
//                            "package:%s",
//                            getApplicationContext<Context>().packageName
//                        )
//                    )
//                    startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO)
//                } catch (e: Exception) {
//                    val intent = Intent()
//                    intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
//                    startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO)
//                }
//            } else {
            //using that function only
            checkSelfPermissions(
                PERMISSIONS_CAMERA,
                REQUEST_CODE_TAKE_PHOTO
            )
//            }
        }
    }

    private fun getPhotoBitmap(uri: Uri): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val fileInputStream = activity?.contentResolver?.openInputStream(uri)
            bitmap = BitmapFactory.decodeStream(fileInputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    private fun getFileSize(uri: Uri): Int {
        var dataSize = 0
        try {
            val fileInputStream = activity?.contentResolver?.openInputStream(uri)
            dataSize = fileInputStream?.available() ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dataSize
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = INTENT_TYPE_GALLERY
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        val mimeTypes = arrayOf(
            FILE_CHOOSER_TYPE_PDF
        )
        intent.type = INTENT_TYPE_FILE_CHOOSER
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, REQUEST_CODE_FILE_CHOOSER)
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            val packageManager: PackageManager? = activity?.packageManager
            if (packageManager != null) {
                takePictureIntent.resolveActivity(packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            AUTHORITY_PHOTO,
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent,
                            REQUEST_CODE_TAKE_PHOTO
                        )
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        Log.e("TAG", "createImageFile: ")
        // Create an image file name
        val timeStamp: String = SimpleDateFormat(IMAGE_DATA_PATTERN, Locale.ENGLISH).format(
            Date()
        )
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            requireContext().getString(R.string.image_name_prefix, timeStamp), /* prefix */
            IMAGE_NAME_SUFFIX, /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun saveImageFileToGallery() {
        Log.e("TAG", "saveImageFileToGallery: ")
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            Log.e("TAG", "saveImageFileToGallery: ${mediaScanIntent.data}")
            activity?.sendBroadcast(mediaScanIntent)
        }
    }

    override fun onAttachFileClick(action: AttachFileAction) {
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
        fun newInstance() = SupportFragment()

        private val PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        private val PERMISSIONS_CAMERA = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )

        private const val AUTHORITY_PHOTO = "com.example.android.fileprovider"

        private const val IMAGE_DATA_PATTERN = "yyyyMMdd_HHmmss"
        private const val IMAGE_NAME_SUFFIX = ".jpg"

        private const val FILE_CHOOSER_TYPE_PDF = "application/pdf"
        private const val FILE_CHOOSER_TYPE_JPG = "image/jpg"
        private const val FILE_CHOOSER_TYPE_PNG = "image/png"

        private const val INTENT_TYPE_GALLERY = "image/*"
        private const val INTENT_TYPE_FILE_CHOOSER = "*/*"

        private const val REQUEST_CODE_FILE_CHOOSER = 111
        private const val REQUEST_CODE_GALLERY = 222
        private const val REQUEST_CODE_TAKE_PHOTO = 333

        private const val UPLOAD_FILE_MAX_SIZE_IN_BYTES = 10_000_000   //10 Mb
    }
}