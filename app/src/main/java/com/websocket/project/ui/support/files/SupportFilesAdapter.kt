package com.websocket.project.ui.support.files

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemSupportFilesFileBinding
import com.websocket.project.databinding.ItemSupportFilesImageBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.humanReadableByteCountSI
import com.websocket.project.ui.base.inflateWithBinding

class SupportFilesAdapter(
    val listener: SupportFilesActionListener
) : BaseRecyclerViewAdapter() {

    val attachedFiles = mutableListOf<SupportFragmentFileType>()

    override fun getItemViewType(position: Int): Int {
        return attachedFiles[position].supportFileViewType.viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            SupportFragmentFileType.IMG -> ImageViewHolder(
                parent.inflateWithBinding(
                    R.layout.item_support_files_image
                )
            )
            else -> FileViewHolder(
                parent.inflateWithBinding(
                    R.layout.item_support_files_file
                )
            )
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
                        val bitmap = img.imageBitmap
                        if (bitmap != null) {
                            supportFilesImageImg.setImageBitmap(
                                bitmap
                            )

                            supportFilesImageImg.setOnClickListener {
                                listener.showPhotoDetail(bitmap)
                            }
                        }
                        supportFilesImageNameText.text = img.name
                        supportFilesImageSizeText.text = humanReadableByteCountSI(img.size)
                        supportFileImageRemoveBtnLayout.setOnClickListener {
                            listener.onRemoveFileClick(item)
                        }
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
                    val document: DocumentModel = item as DocumentModel
                    with(it) {
                        supportFragmentFileBinding = document
                        supportFilesFileNameText.text = document.name
                        supportFilesFileSizeText.text = humanReadableByteCountSI(document.size)
                        supportFilesFileRemoveBtnLayout.setOnClickListener {
                            listener.onRemoveFileClick(item)
                        }

                        supportFilesFileLayout.setOnClickListener {
                            listener.showPdfFile(document.url)
                        }
                    }
                }
            }
        }
    }

    fun addItem(item: SupportFragmentFileType) {
        attachedFiles.add(item)
        notifyItemInserted(attachedFiles.size)
    }

    fun removeAllItems() {
        attachedFiles.clear()
        notifyDataSetChanged()
    }

    fun removeItem(item: SupportFragmentFileType) {
        attachedFiles.remove(item)
        notifyDataSetChanged()
    }

    override fun getItemCount() = attachedFiles.size
}