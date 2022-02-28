package com.websocket.project.ui.support.attach_file

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemAttachFileActionBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding

class AttachFileBottomSheetAdapter(
    val attachFileBottomSheetListener: AttachFileBottomSheetListener
): BaseRecyclerViewAdapter() {

    val attachFileActionsList = AttachFileAction.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return CharacterNewItemViewHolder(parent.inflateWithBinding(R.layout.item_attach_file_action))
    }

    override fun getItemCount(): Int = attachFileActionsList.size

    private inner class CharacterNewItemViewHolder(private val itemAttachFileActionBinding: ItemAttachFileActionBinding) : BaseViewHolder(itemAttachFileActionBinding) {
        override fun bind(position: Int) {

            val attackFileAction = attachFileActionsList[position]
            with(itemAttachFileActionBinding) {

                itemAttachFileActionImg.setImageResource(attackFileAction.icon)

                itemAttachFileActionText.text =
                    itemAttachFileActionText.context.getText(attackFileAction.title)

                itemAttachFileActionLayout.setOnClickListener {
                    attachFileBottomSheetListener.onAttachFileClick(attackFileAction)
                }
            }
        }
    }
}