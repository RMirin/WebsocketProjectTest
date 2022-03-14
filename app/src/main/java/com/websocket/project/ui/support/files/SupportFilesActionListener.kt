package com.websocket.project.ui.support.files

import android.graphics.Bitmap
import android.net.Uri

interface SupportFilesActionListener {
    fun onRemoveFileClick(item: SupportFragmentFileType)
    fun showPhotoDetail(bitmap: Bitmap)
    fun showPdfFile(uri: Uri?)
}