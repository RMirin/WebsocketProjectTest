package com.websocket.project.ui.support.attach_file

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.websocket.project.R

enum class AttachFileAction(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    CAMERA(
        R.string.attach_file_bottom_sheet_camera,
        R.drawable.ic_attach_file_bottom_sheet_camera
    ),
    GALLERY(
        R.string.attach_file_bottom_sheet_gallery,
        R.drawable.ic_attach_file_bottom_sheet_gallery
    ),
    DOCUMENT(
        R.string.attach_file_bottom_sheet_document,
        R.drawable.ic_attach_file_bottom_sheet_document
    )
}