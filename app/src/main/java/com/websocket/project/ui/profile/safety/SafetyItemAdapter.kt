package com.websocket.project.ui.profile.safety

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.websocket.project.R
import com.websocket.project.databinding.ItemSafetyBinding
import com.websocket.project.databinding.ItemSafetyWithSwtichBinding

class SafetyItemAdapter(
    private val safetyItemList: Array<SafetyItem>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class SafetyItemWithSwitchViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSafetyWithSwtichBinding.bind(itemView)

        fun bind(safetyItem: SafetyItem) {
            with(binding) {
                itemSafetyName.text = itemSafetyName.context.getText(safetyItem.nameItem)
            }
        }
    }

    inner class SafetyItemWithoutSwitchViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSafetyBinding.bind(itemView)

        fun bind(safetyItem: SafetyItem) {
            with(binding) {
                itemSafetyName.text = itemSafetyName.context.getText(safetyItem.nameItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (safetyItemList[position].withSwitch) {
            true -> 0
            false -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_safety_with_swtich, parent, false)
                return SafetyItemWithSwitchViewHolder(itemView)
            }
            1 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_safety, parent, false)
                return SafetyItemWithoutSwitchViewHolder(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_safety, parent, false)
                return SafetyItemWithoutSwitchViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                (holder as SafetyItemWithSwitchViewHolder).bind(safetyItemList[position])
            }
            1 -> {
                (holder as SafetyItemWithoutSwitchViewHolder).bind(safetyItemList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return safetyItemList.size
    }
}