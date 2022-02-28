package com.websocket.project.ui.main.crypto

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemMainActionBinding
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding

class MainFragmentActionAdapter(
    private val mainFragmentActionsListener: MainFragmentActionsListener
): BaseRecyclerViewAdapter() {

    private val mainFragmentActionsList = mutableListOf<MainFragmentAction>()

    fun setActions(cryptoActions: Array<MainFragmentAction>) {
        this.mainFragmentActionsList.apply {
            clear()
            addAll(cryptoActions)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return CharacterNewItemViewHolder(parent.inflateWithBinding(R.layout.item_main_action))
    }

    override fun getItemCount(): Int = mainFragmentActionsList.size

    private inner class CharacterNewItemViewHolder(private val itemMainActionBinding: ItemMainActionBinding) : BaseViewHolder(itemMainActionBinding) {
        override fun bind(position: Int) {

            val mainFragmentAction = mainFragmentActionsList[position]

            with(itemMainActionBinding) {
                mainActionText.text = this.root.context.getText(mainFragmentAction.title)
                mainActionImg.setImageResource(mainFragmentAction.icon)
                mainActionLayout.setOnClickListener {
                    mainFragmentActionsListener.onMainFragmentActionClick(mainFragmentAction)
                }
            }
        }
    }
}

interface MainFragmentActionsListener {
    fun onMainFragmentActionClick(mainFragmentAction: MainFragmentAction)
}