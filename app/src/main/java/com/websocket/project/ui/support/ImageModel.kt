package com.websocket.project.ui.support

import android.graphics.Bitmap

class ImageModel (
    var size: Int = 0,
    var name: String = "",
    var imageBitmap: Bitmap? = null
): SupportFragmentFileType {
    override val supportFileViewType: SupportFileViewType
        get() = SupportFileViewType.IMG
}