package com.websocket.project.ui.profile.settings.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.websocket.project.R
import com.websocket.project.databinding.ItemNotificationSwitchBinding
import com.websocket.project.databinding.ItemNotificationTitleBinding

class SettingsNotificationAdapter(
    private val notificationItem: Array<NotificationItem>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class NotificationItemWithSwitchViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNotificationSwitchBinding.bind(itemView)

        fun bind(notificationItem: NotificationItem) {
            with(binding) {
                itemNotificationName.text = itemNotificationName.context.getText(notificationItem.nameItem)
            }
        }
    }

    inner class NotificationItemTitleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNotificationTitleBinding.bind(itemView)

        fun bind(notificationItem: NotificationItem) {
            with(binding) {
                itemNotificationTitle.text = itemNotificationTitle.context.getText(notificationItem.nameItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (notificationItem[position].withSwitch) {
            true -> 0
            false -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_notification_switch, parent, false)
                return NotificationItemWithSwitchViewHolder(itemView)
            }
            1 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_notification_title, parent, false)
                return NotificationItemTitleViewHolder(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_notification_title, parent, false)
                return NotificationItemTitleViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                (holder as NotificationItemWithSwitchViewHolder).bind(notificationItem[position])
            }
            1 -> {
                (holder as NotificationItemTitleViewHolder).bind(notificationItem[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return notificationItem.size
    }
}