package com.websocket.project.ui.profile.settings.settings_main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.websocket.project.R
import com.websocket.project.databinding.ItemSettingsBinding
import com.websocket.project.databinding.ItemSettingsWithIconBinding

class SettingsMainItemAdapter(
    private val settingsItemList: Array<SettingsItem>,
    private val settingsMainItemOnClick: SettingsMainItemOnClick
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class SettingsMainItemWithFlagViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSettingsWithIconBinding.bind(itemView)

        fun bind(settingsItem: SettingsItem) {
            with(binding) {
                itemSettingsFlagIv.setImageResource(R.drawable.ic_flag_ru)
                itemSettingsName.text = itemSettingsName.context.getText(settingsItem.nameItem)

                root.setOnClickListener {
                    settingsMainItemOnClick.itemClick(settingsItem)
                }
            }
        }
    }

    inner class SettingsMainItemWithoutFlagViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSettingsBinding.bind(itemView)

        fun bind(settingsItem: SettingsItem) {
            with(binding) {
                itemSettingsName.text = itemSettingsName.context.getText(settingsItem.nameItem)

                root.setOnClickListener {
                    settingsMainItemOnClick.itemClick(settingsItem)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (settingsItemList[position].withFlag) {
            true -> 0
            false -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_settings_with_icon, parent, false)
                return SettingsMainItemWithFlagViewHolder(itemView)
            }
            1 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_settings, parent, false)
                return SettingsMainItemWithoutFlagViewHolder(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_settings, parent, false)
                return SettingsMainItemWithoutFlagViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                (holder as SettingsMainItemWithFlagViewHolder).bind(settingsItemList[position])
            }
            1 -> {
                (holder as SettingsMainItemWithoutFlagViewHolder).bind(settingsItemList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return settingsItemList.size
    }
}