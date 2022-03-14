package com.websocket.project.ui.support.files

import android.net.Uri

class DocumentModel (
    var url : Uri?,
): BaseFileModel(), SupportFragmentFileType {
    override val supportFileViewType: SupportFileViewType
        get() = SupportFileViewType.FILE_PDF
}