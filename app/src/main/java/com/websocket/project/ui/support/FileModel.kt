package com.websocket.project.ui.support

import android.net.Uri

class FileModel (
    var url : Uri?,
    var size: Int = 0,
    var name: String = ""
): SupportFragmentFileType {
    override val supportFileViewType: SupportFileViewType
        get() = SupportFileViewType.FILE_PDF
}