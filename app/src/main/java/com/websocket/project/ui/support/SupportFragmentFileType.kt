package com.websocket.project.ui.support

interface SupportFragmentFileType {

    val supportFileViewType: SupportFileViewType

    companion object {
        const val IMG = 0
        const val FILE_PDF = 1
    }
}