package com.websocket.project.ui.support.files

import android.graphics.Bitmap

class ImageModel (
    var imageBitmap: Bitmap? = null
): BaseFileModel(), SupportFragmentFileType {
    override val supportFileViewType: SupportFileViewType
        get() = SupportFileViewType.IMG
}