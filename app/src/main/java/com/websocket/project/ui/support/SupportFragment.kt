package com.websocket.project.ui.support

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.websocket.project.R
import com.websocket.project.databinding.FragmentSupportBinding
import com.websocket.project.ui.base.*
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.support.appeal_category.AppealCategory
import com.websocket.project.ui.support.attach_file.AttachFileAction
import com.websocket.project.ui.support.attach_file.AttachFileBottomSheetFragment
import com.websocket.project.ui.support.attach_file.AttachFileBottomSheetListener
import com.websocket.project.ui.support.files.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class SupportFragment : BaseFragment<FragmentSupportBinding>(), AttachFileBottomSheetListener,
    SupportFragmentActionListener,
    SupportFilesActionListener,
    DialogFragmentSupportActionListener {

    private val viewModel: SupportFragmentViewModel by viewModels()

    lateinit var currentPhotoPath: String

    private val supportFilesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SupportFilesAdapter(this)
    }

    private val attackFileBottomSheetFragment by lazy(LazyThreadSafetyMode.NONE) {
        AttachFileBottomSheetFragment(this@SupportFragment)
    }

    override fun initViewBinding(): FragmentSupportBinding =
        FragmentSupportBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(APPEAL_RESULT_KEY)?.observe(viewLifecycleOwner) { result ->
            viewModel.setSelectedAppealCategory(result)
        }

        with(binding) {
            supportActionListenerBinding = this@SupportFragment
            supportViewModelBinding = viewModel

            supportFilesRecycler.adapter = supportFilesAdapter

            supportSubjectOfAppealEditText.doOnTextChanged { text, _, _, count ->
                viewModel.subjectOfAppeal.value = text.toString()
                viewModel.setSubjectMaxSymbolsCount(requireContext(), count)
            }

            supportTextOfAppealEditText.doOnTextChanged { text, _, _, count ->
                viewModel.textOfAppeal.value = text.toString()
                viewModel.setTextOfAppealMaxSymbolsCount(requireContext(), count)
            }

            supportTextOfAppealEditText.setOnFocusChangeListener { _, onFocus ->
                if (onFocus) {
                    validateCategoryAndSubjectFields()
                }
            }
        }

        observeLiveData()
    }

    private fun validateCategoryAndSubjectFields() {
        viewModel.formIsValid.launchWhenStarted(lifecycleScope) { formIsValid ->
            binding.supportSendBtn.isEnabled = formIsValid
        }
    }

    private fun observeLiveData() {
        with(viewModel) {

            observe(attachedFilesCount) { filesCount ->
                with(binding.supportAttachFileText) {
                    text = requireContext().getString(R.string.support_attach, filesCount)
                    spanAll(
                        ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.white)),
                        requireContext().getString(R.string.support_attach_file_span)
                    )
                }
            }

            observe(appealCategoryChosenPosition) { appealCategoryPosition ->
                if (appealCategoryPosition != -1) {
                    with(binding.supportCategoryOfAppealBtn) {
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        text =
                            requireContext().getText(AppealCategory.values()[appealCategoryPosition].title)
                    }
                }
            }
        }
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
            attackFileBottomSheetFragment.dismiss()
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
            Log.e(
                "TAG",
                "onActivityResult: resiult ok data ${data?.data} request code $requestCode"
            )

            when (requestCode) {
                REQUEST_CODE_FILE_CHOOSER -> {
                    val uri: Uri? = data?.data
                    Log.e("TAG", "FILE_CHOOSER_REQUEST_CODE onActivityResult: $uri")
                    showFileFromUri(uri)
                    val scheme: String? = uri?.scheme
                    if (scheme.equals(ContentResolver.SCHEME_CONTENT) && uri != null) {
                        val fileSize = getFileSize(uri)
                        if (viewModel.checkIsSizeAvailable(fileSize)) {
                            addFileToAdapter(
                                DocumentModel(
                                    url = uri
                                ).apply {
                                    name = getRealPathImageFromUri(uri) ?: ""
                                    size = fileSize
                                    url = uri
                                })
                        } else {
                            showErrorToast(R.string.support_file_too_large_msg)
                        }
                    }
                }
                REQUEST_CODE_GALLERY -> {
                    val uri: Uri? = data?.data
                    Log.e("TAG", "GALLERY_REQUEST_CODE onActivityResult: $uri")
                    if (uri != null) {
                        val fileSize = getFileSize(uri)
                        if (viewModel.checkIsSizeAvailable(fileSize)) {
                            addFileToAdapter(
                                ImageModel(
                                    imageBitmap = getPhotoBitmap(uri)
                                ).apply {
                                    name = getRealPathImageFromUri(uri) ?: ""
                                    size = fileSize
                                })
                        } else {
                            showErrorToast(R.string.support_file_too_large_msg)
                        }
                    }
                }
                REQUEST_CODE_TAKE_PHOTO -> {
                    saveImageFileToGallery()
                    if (currentPhotoPath.isNotEmpty()) {
                        val photoFile = File(currentPhotoPath)
                        val photoUri = Uri.fromFile(photoFile)
                        val fileSize = getFileSize(photoUri)
                        if (viewModel.checkIsSizeAvailable(fileSize)) {
                            addFileToAdapter(
                                ImageModel(
                                    imageBitmap = getPhotoBitmap(photoUri),
                                ).apply {
                                    name = getRealPathImageFromUri(photoUri) ?: ""
                                    size = fileSize
                                })
                        } else {
                            showErrorToast(R.string.support_file_too_large_msg)
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
                    showErrorToast(R.string.support_storage_access_msg)
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

    private fun addFileToAdapter(item: SupportFragmentFileType) {
        supportFilesAdapter.addItem(item)
        viewModel.increaseAttachedFilesCount()
    }

    private fun checkSelfPermissions(permissions: Array<String>, permissionsRequestCode: Int) {
        requestPermissions(
            permissions,
            permissionsRequestCode
        )
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

        if (bitmap != null) {
            return rotateImageIfRequired(bitmap, uri)
        }
        return bitmap
    }

    private fun getFileSize(uri: Uri): Int {
        var dataSize = 0
        try {
            val fileInputStream = activity?.contentResolver?.openInputStream(uri)
            dataSize = fileInputStream?.available() ?: UPLOAD_FILE_MAX_SIZE_IN_BYTES
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dataSize
    }

    private fun getFileName(path: String): String {
        return path.substring(path.lastIndexOf("/")+1)
    }

    @SuppressLint("Range")
    fun getRealPathImageFromUri(uri: Uri): String? {
        var res: String? = null
        if (uri.scheme.equals("content")) {
            val cursor = (activity as MainActivity).contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
            if (res == null) {
                res = uri.path
                val cutt = res?.lastIndexOf('/')
                if (cutt != -1) {
                    res = res?.substring(cutt?.plus(1) ?: 0)
                }
            }
        }
        return res
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
                            AUTHORITY_FILE_PROVIDER,
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(
                            takePictureIntent,
                            REQUEST_CODE_TAKE_PHOTO
                        )
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
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
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            Log.e("TAG", "saveImageFileToGallery: ${mediaScanIntent.data}")
            activity?.sendBroadcast(mediaScanIntent)
        }
    }

    /**
     * Rotate an image if required.
     *
     * @param img           The image bitmap
     * @param selectedImage Image URI
     * @return The resulted Bitmap after manipulation
     */
    @Throws(IOException::class)
    private fun rotateImageIfRequired(img: Bitmap, selectedImage: Uri): Bitmap? {
        var orientation = 0
        try {
            activity?.contentResolver?.openInputStream(selectedImage).use { inputStream ->
                val exif = ExifInterface(inputStream!!)
                orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, IMAGE_ROTATION_DEGREE_90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, IMAGE_ROTATION_DEGREE_180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, IMAGE_ROTATION_DEGREE_270)
            else -> img
        }
    }

    private fun rotateImage(img: Bitmap, degree: Int): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return rotatedImg
    }

    private fun showPhotoBitmap(bitmap: Bitmap) {
        DialogFragmentSupportImageDetail(
            bitmap
        ).show(
            (activity as MainActivity).supportFragmentManager,
            KEY_DIALOG_IMAGE_DETAIL
        )
    }

    private fun showErrorToast(magStringId: Int) {
        Toast.makeText(
            requireContext(),
            magStringId,
            Toast.LENGTH_SHORT
        ).show()
    }

//    fun readFile(context: Context, filename: String?): String? {
//        return try {
//            val fis: FileInputStream = context.openFileInput(filename)
//            val isr = InputStreamReader(fis, "UTF-8")
//            val bufferedReader = BufferedReader(isr)
//            val sb = StringBuilder()
//            var line: String?
//            while (bufferedReader.readLine().also { line = it } != null) {
//                sb.append(line).append("\n")
//            }
//            sb.toString()
//        } catch (e: FileNotFoundException) {
//            ""
//        } catch (e: UnsupportedEncodingException) {
//            ""
//        } catch (e: IOException) {
//            ""
//        }
//    }

//    @Throws(IOException::class)
//    fun getFile(context: Context, uri: Uri?): File? {
//        val destinationFilename =
//            File(context.filesDir.path + File.separatorChar + uri?.let { queryName(context, it) })
//        try {
//            context.contentResolver.openInputStream(uri!!).use { ins ->
//                createFileFromStream(
//                    ins!!,
//                    destinationFilename
//                )
//            }
//        } catch (ex: java.lang.Exception) {
//            Log.e("Save File", ex.message!!)
//            ex.printStackTrace()
//        }
//        return destinationFilename
//    }

//    fun createFileFromStream(ins: InputStream, destination: File?) {
//        try {
//            FileOutputStream(destination).use { os ->
//                val buffer = ByteArray(4096)
//                var length: Int
//                while (ins.read(buffer).also { length = it } > 0) {
//                    os.write(buffer, 0, length)
//                }
//                os.flush()
//            }
//        } catch (ex: java.lang.Exception) {
//            Log.e("Save File", ex.message!!)
//            ex.printStackTrace()
//        }
//    }

    private fun queryName(context: Context, uri: Uri): String? {
        val returnCursor: Cursor = context.contentResolver.query(uri, null, null, null, null)!!
        val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name: String = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }

    private fun showFileFromUri(uri: Uri?) {
        if (uri?.path != null) {
            val localPath: String? = uri.path
            val realPath = URLDecoder.decode(uri.toString(), StandardCharsets.UTF_8.toString())
            Log.e("TAG", "showFileFromUri path ${uri.path} ")
            val file = File(realPath)
            val dir = file.parent
            val dirAsFile = file.parentFile
            Log.e("TAG", "showFileFromUri: absolute ${file.absolutePath}")
//        val file = getFile(requireContext(), uri)

            if (dirAsFile != null && dirAsFile.exists()) {
                Log.e("TAG", "showFileFromUri: file ${file}")
                val excelPath: Uri =
                    FileProvider.getUriForFile(requireContext(), AUTHORITY_FILE_PROVIDER, file)

                val pdfIntent = Intent(Intent.ACTION_VIEW)
                pdfIntent.setDataAndType(excelPath, "application/pdf")
                pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                try {
                    startActivity(pdfIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        requireContext(),
                        "No Application available to view PDF",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                Log.e("TAG", "showFileFromUri: file is not exist")
            }
        } else {
            Log.e("TAG", "showFileFromUri: path file is null")
        }
    }

    override fun onAttachFileClick(action: AttachFileAction) {
        when (action) {
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

    override fun onRemoveFileClick(item: SupportFragmentFileType) {
        DialogFragmentSupportAction(
            requireContext().getString(
                R.string.support_dialog_delete_file,
                (item as BaseFileModel).name,
                humanReadableByteCountSI((item as BaseFileModel).size)
            ),
            this,
            SupportAction.DELETE_FILE_OK,
            item
        ).show(
            (activity as MainActivity).supportFragmentManager,
            KEY_DIALOG
        )
    }

    override fun showPhotoDetail(bitmap: Bitmap) {
        showPhotoBitmap(bitmap)
    }

    override fun showPdfFile(uri: Uri?) {
        if (uri != null) {
            showFileFromUri(uri)
        }
    }

    override fun selectCategoryOfAppealBtnClick() {
        findNavController().navigate(SupportFragmentDirections.actionSupportFragmentToAppealCategoryFragment(viewModel.getSelectedAppealCategoryPosition()))
    }

    override fun backBtnClick() {
        (activity as MainActivity).onBackPressed()
    }

    override fun supportSendBtnClick() {
    }

    override fun attachFilesBtnClick() {
        if (viewModel.getAttachedFilesCount() < UPLOAD_FILE_MAX_COUNT) {
            attackFileBottomSheetFragment.show(
                (activity as MainActivity).supportFragmentManager,
                KEY_DIALOG_BOTTOM
            )
        }
    }

    override fun deleteFileActionOk(fileToDelete: SupportFragmentFileType) {
        supportFilesAdapter.removeItem(fileToDelete)
        viewModel.decreaseAttachedFilesCount((fileToDelete as BaseFileModel).size)
    }

    override fun deleteAllFilesBtnClick() {
        DialogFragmentSupportAction(
            requireContext().getString(R.string.support_dialog_delete_all_files),
            this,
            SupportAction.DELETE_FILES_OK,
            null
        ).show(
            (activity as MainActivity).supportFragmentManager,
            KEY_DIALOG
        )
    }

    override fun deleteAllFilesActionOk() {
        supportFilesAdapter.removeAllItems()
        viewModel.removeAllAttachedFiles()
    }

    override fun deleteTextOfAppealBtnClick() {
        DialogFragmentSupportAction(
            requireContext().getString(R.string.support_dialog_delete_text),
            this,
            SupportAction.DELETE_TEXT_OK,
            null
        ).show(
            (activity as MainActivity).supportFragmentManager,
            KEY_DIALOG
        )
    }

    override fun deleteTextOfAppealActionOk() {
        binding.supportTextOfAppealEditText.text.clear()
    }

    companion object {
        private val PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        private val PERMISSIONS_CAMERA = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )

        private const val AUTHORITY_FILE_PROVIDER = "com.example.android.fileprovider"

        private const val IMAGE_DATA_PATTERN = "yyyyMMdd_HHmmss"
        private const val IMAGE_NAME_SUFFIX = ".jpg"
        private const val IMAGE_ROTATION_DEGREE_90 = 90
        private const val IMAGE_ROTATION_DEGREE_180 = 180
        private const val IMAGE_ROTATION_DEGREE_270 = 270

        private const val FILE_CHOOSER_TYPE_PDF = "application/pdf"
        private const val FILE_CHOOSER_TYPE_JPG = "image/jpg"
        private const val FILE_CHOOSER_TYPE_PNG = "image/png"

        private const val INTENT_TYPE_GALLERY = "image/*"
        private const val INTENT_TYPE_FILE_CHOOSER = "*/*"

        const val UPLOAD_FILE_MAX_SIZE_IN_BYTES = 10_000_000   //10 Mb
        const val UPLOAD_FILE_MAX_COUNT = 12

        private const val REQUEST_CODE_FILE_CHOOSER = 111
        private const val REQUEST_CODE_GALLERY = 222
        private const val REQUEST_CODE_TAKE_PHOTO = 333

        const val APPEAL_RESULT_KEY = "appeal_category"

        private const val KEY_DIALOG_BOTTOM = "bottom_dialog"
        private const val KEY_DIALOG_IMAGE_DETAIL = "image_detail_dialog"
        private const val KEY_DIALOG = "dialog"
    }
}