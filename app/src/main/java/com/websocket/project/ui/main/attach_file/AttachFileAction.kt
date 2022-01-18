package com.websocket.project.ui.main.attach_file

import androidx.annotation.StringRes
import com.websocket.project.R

enum class AttachFileAction(
    @StringRes val title: Int
) {
    CAMERA(
        R.string.action_bottom_sheet_camera
    ),
    GALLERY(
        R.string.action_bottom_sheet_gallery
    ),
    DOCUMENT(
        R.string.action_bottom_sheet_document
    )
}