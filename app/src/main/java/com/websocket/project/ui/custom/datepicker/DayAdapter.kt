package com.websocket.project.ui.custom.datepicker

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.websocket.project.R
import com.websocket.project.databinding.ItemDatePickerDayBinding

class DayAdapter(
    val listener: DayAdapterListener
) : RecyclerView.Adapter<DayAdapter.DayItemViewHolder>() {

    private val dayInMonthList = mutableListOf<DatePickerDay>()
    private var chosenDateDay: DatePickerDay = DatePickerDay(0)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayAdapter.DayItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_date_picker_day, parent, false)
        return DayItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = dayInMonthList.size

    fun setChosen(position: Int) {
        val viewDayDate: DatePickerDay = dayInMonthList[position]
        chosenDateDay = viewDayDate
        listener.setChosenDay(chosenDateDay, position)
        notifyItemChanged(position)
        notifyDataSetChanged()
    }

    fun setData(datePickerDays: MutableList<DatePickerDay>) {
        dayInMonthList.clear()
        dayInMonthList.addAll(datePickerDays)
        Log.e("TAG", "setData days ${datePickerDays.size}")
        notifyDataSetChanged()
    }

    inner class DayItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDatePickerDayBinding.bind(itemView)
        fun bind(viewDayItemDate: DatePickerDay?) {

            val rootContext = binding.root.context

            with(binding) {
                if (viewDayItemDate == chosenDateDay) {
                    datePickerDayText.setTextColor(rootContext.getColor(R.color.white))
                } else {
                    datePickerDayText.setTextColor(rootContext.getColor(R.color.gray))
                }
                datePickerDayText.text = viewDayItemDate?.dayNumber.toString()
            }
        }
    }

    override fun onBindViewHolder(holder: DayAdapter.DayItemViewHolder, position: Int) {
        if (dayInMonthList.size == 0) {
            holder.bind(null)
        } else {
            val viewDayListDate: DatePickerDay = dayInMonthList[position % dayInMonthList.size]
            holder.bind(viewDayListDate)
        }
    }
}