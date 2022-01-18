package com.websocket.project.ui.profile.profile_main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.websocket.project.R
import com.websocket.project.databinding.ItemProfileBinding

class ProfileItemAdapter(
    private val itemProfileItemList: Array<ProfileItem>,
    private val profileItemClick: ProfileItemClick
) :
    RecyclerView.Adapter<ProfileItemAdapter.ProfileItemViewHolder>() {


    inner class ProfileItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemProfileBinding.bind(itemView)

        fun bind(profileItem: ProfileItem) {
            with(binding) {
                itemProfileIcon.setImageResource(profileItem.itemIcon)
                itemProfileName.text = itemProfileName.context.getText(profileItem.itemName)
                root.setOnClickListener {
                    profileItemClick.itemClick(profileItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profile, parent, false)
        return ProfileItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProfileItemViewHolder, position: Int) {
        holder.bind(itemProfileItemList[position])
    }

    override fun getItemCount(): Int {
        return itemProfileItemList.size
    }
}