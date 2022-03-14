package com.websocket.project.ui.support

import com.websocket.project.ui.support.files.SupportFragmentFileType

interface DialogFragmentSupportActionListener {
    fun deleteAllFilesActionOk()
    fun deleteTextOfAppealActionOk()
    fun deleteFileActionOk(fileToDelete: SupportFragmentFileType)
}