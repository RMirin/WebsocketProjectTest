package com.websocket.project.ui.support.files

interface SupportFragmentFileType {

    val supportFileViewType: SupportFileViewType

    companion object {
        const val IMG = 0
        const val FILE_PDF = 1
    }
}