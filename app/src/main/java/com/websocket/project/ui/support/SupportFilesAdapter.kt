package com.websocket.project.ui.support

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemSupportFilesFileBinding
import com.websocket.project.databinding.ItemSupportFilesImageBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding

class SupportFilesAdapter(): BaseRecyclerViewAdapter() {

    val attachedFiles = mutableListOf<SupportFragmentFileType>()

    override fun getItemViewType(position: Int): Int {
        return attachedFiles[position].supportFileViewType.viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            SupportFragmentFileType.IMG -> ImageViewHolder(parent.inflateWithBinding(
                R.layout.item_support_files_image
            ))
            else -> FileViewHolder(parent.inflateWithBinding(
                R.layout.item_support_files_file
            ))
        }
    }

    private inner class ImageViewHolder(
        private val itemSupportFilesImageBinding: ItemSupportFilesImageBinding
    ) : BaseViewHolder(itemSupportFilesImageBinding) {
        override fun bind(position: Int) {
            val item = attachedFiles[position]

            itemSupportFilesImageBinding.also {
                if (item.supportFileViewType.viewType == SupportFragmentFileType.IMG) {
                    val img: ImageModel = item as ImageModel
                    with(it) {
                        if (img.imageBitmap != null) {
                            supportFilesImageImg.setImageBitmap(
                                img.imageBitmap
                            )
                        }
                        supportFilesImageNameText.text = img.name
                        supportFilesImageSizeText.text = img.size.toString()
                    }
                }
            }
        }
    }

    private inner class FileViewHolder(
        private val itemSupportFilesFileBinding: ItemSupportFilesFileBinding
    ) : BaseViewHolder(itemSupportFilesFileBinding) {
        override fun bind(position: Int) {
            val item = attachedFiles[position]

            itemSupportFilesFileBinding.also {
                if (item.supportFileViewType.viewType == SupportFragmentFileType.FILE_PDF) {
                    val file: FileModel = item as FileModel
                    with(it) {
                        supportFilesFileNameText.text = file.name
                        supportFilesFileSizeText.text = file.size.toString()
                    }
                }
            }
        }
    }

    fun addItem(item: SupportFragmentFileType) {
        attachedFiles.add(item)
        notifyItemInserted(attachedFiles.size)
    }

    override fun getItemCount() = attachedFiles.size
}