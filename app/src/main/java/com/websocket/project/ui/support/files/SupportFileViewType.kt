package com.websocket.project.ui.support.files

import com.google.gson.annotations.SerializedName

enum class SupportFileViewType(val viewType: Int) {
    @SerializedName("img")
    IMG(0),
    @SerializedName("file_pdf")
    FILE_PDF(1)
}