package com.websocket.project.ui.custom.datepicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.websocket.project.R
import com.websocket.project.databinding.ItemDatePickerMonthBinding

class MonthAdapter(
    val listener: MonthAdapterListener
) : RecyclerView.Adapter<MonthAdapter.MonthItemViewHolder>() {

    private val monthList = mutableListOf<DatePickerMonth>()
    private var chosenDateMonth: DatePickerMonth = DatePickerMonth.JANUARY

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_date_picker_month, parent, false)
        return MonthItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = Integer.MAX_VALUE

    fun setChosen(position: Int) {
        val viewMonthDate: DatePickerMonth = monthList[position % monthList.size]
        chosenDateMonth = viewMonthDate
        listener.setChosenMonth(chosenDateMonth)
        notifyDataSetChanged()
    }

    fun setData(datePickerMonths: MutableList<DatePickerMonth>) {
        monthList.addAll(datePickerMonths)
        notifyDataSetChanged()
    }

    inner class MonthItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDatePickerMonthBinding.bind(itemView)
        fun bind(viewMonthItemDate: DatePickerMonth?) {

            val rootContext = binding.root.context

            with(binding) {
                if (viewMonthItemDate == chosenDateMonth) {
                    datePickerMonthText.setTextColor(rootContext.getColor(R.color.white))
                } else {
                    datePickerMonthText.setTextColor(rootContext.getColor(R.color.gray))
                }
                datePickerMonthText.text = viewMonthItemDate?.monthName?.let { rootContext.getString(it) }
            }
        }
    }

    override fun onBindViewHolder(holder: MonthItemViewHolder, position: Int) {
        if (monthList.size == 0) {
            holder.bind(null)
        } else {
            val viewMonthListDate: DatePickerMonth = monthList[position % monthList.size]
            holder.bind(viewMonthListDate)
        }
    }
}