package com.websocket.project.ui.support.appeal_category

import android.view.ViewGroup
import com.websocket.project.R
import com.websocket.project.databinding.ItemAppealCategoryBinding
import com.websocket.project.ui.base.*

class AppealCategoryAdapter(
    private val appealCategoryAdapterListener: AppealCategoryAdapterActionListener,
    initCheckedValue: Int
) : BaseRecyclerViewAdapter() {

    private var initValue = initCheckedValue
    private val appealCategoryList = AppealCategory.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return AppealCategoryViewHolder(parent.inflateWithBinding(R.layout.item_appeal_category))
    }

    private inner class AppealCategoryViewHolder(
        private val itemAppealCategoryBinding: ItemAppealCategoryBinding
    ) : BaseViewHolder(itemAppealCategoryBinding) {
        override fun bind(position: Int) {
            with(itemAppealCategoryBinding) {
                val appealCategoryItem = appealCategoryList[position]

                appealCategoryText.isChecked = (initValue == position)
                appealCategoryText.text =
                    appealCategoryText.context.getText(appealCategoryItem.title)
                appealCategoryText.setOnClickListener {
                    appealCategoryAdapterListener.onAppealCategorySelected(appealCategoryItem)
                }
            }
        }
    }

    override fun getItemCount(): Int = appealCategoryList.size

    fun onAppealCategorySelected(position: Int) {
        initValue = position
        notifyDataSetChanged()
    }
}