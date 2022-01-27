package com.websocket.project.ui.main.attach_file

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemAttachFileActionBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding

class AttachFileBottomSheetAdapter(
    val attachFileBottomSheetListener: AttachFileBottomSheetListener
): BaseRecyclerViewAdapter() {

    val filterActionsList = mutableListOf<AttachFileAction>()

    fun setFilterActions(filterActions: Array<AttachFileAction>) {
        this.filterActionsList.apply {
            addAll(filterActions)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return CharacterNewItemViewHolder(parent.inflateWithBinding(R.layout.item_attach_file_action))
    }

    override fun getItemCount(): Int = filterActionsList.size

    private inner class CharacterNewItemViewHolder(private val itemAttachFileActionBinding: ItemAttachFileActionBinding) : BaseViewHolder(itemAttachFileActionBinding) {
        override fun bind(position: Int) {

            val itemFilterAction = filterActionsList[position]
            with(itemAttachFileActionBinding) {
                itemFilterActionText.text =
                    itemFilterActionText.context.getText(itemFilterAction.title)

                itemFilterActionLayout.setOnClickListener {
                    attachFileBottomSheetListener.onAttachFileClick(itemFilterAction)
                }
            }
        }
    }
}