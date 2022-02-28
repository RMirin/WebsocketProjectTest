package com.websocket.project.ui.transaction_history

import com.websocket.project.ui.base.BaseRecyclerViewAdapter

class TransactionHistoryAdapter(
    private val appealCategoryListener: AppealCategoryActionListener,
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
                    appealCategoryListener.onAppealCategorySelected(appealCategoryItem)
                }
            }
        }
    }

    override fun getItemCount(): Int = appealCategoryList.size

    fun onAppealCategorySelected(position: Int) {
        initValue = position
        notifyDataSetChanged()
    }

    interface AppealCategoryActionListener {
        fun onAppealCategorySelected(appealCategory: AppealCategory)
    }
}