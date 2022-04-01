package com.websocket.project.ui.custom.datepicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.websocket.project.R
import com.websocket.project.databinding.ItemDatePickerYearBinding

class YearAdapter(
    val listener: YearAdapterListener
): RecyclerView.Adapter<YearAdapter.YearItemViewHolder>() {

    private val yearsList = mutableListOf<DatePickerYear>()
    private var chosenDateYear: DatePickerYear = DatePickerYear(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearAdapter.YearItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_date_picker_year, parent, false)
        return YearItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = Integer.MAX_VALUE

    fun setChosen(position: Int) {
        val viewYearDate: DatePickerYear = yearsList[position % yearsList.size]
        chosenDateYear = viewYearDate
        listener.setChosenYear(chosenDateYear)
        notifyDataSetChanged()
    }

    fun setData(yearStart: Int, datePickerViewYears: MutableList<DatePickerYear>) {
        chosenDateYear = DatePickerYear(yearStart)
        yearsList.addAll(datePickerViewYears)
        notifyDataSetChanged()
    }

    inner class YearItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDatePickerYearBinding.bind(itemView)
        fun bind(viewYearItemDate: DatePickerYear?) {

            val rootContext = binding.root.context

            with(binding) {
                if (viewYearItemDate == chosenDateYear) {
                    datePickerYearText.setTextColor(rootContext.getColor(R.color.white))
                } else {
                    datePickerYearText.setTextColor(rootContext.getColor(R.color.grey))
                }
                datePickerYearText.text = viewYearItemDate?.number.toString()
            }
        }
    }

    override fun onBindViewHolder(holder: YearItemViewHolder, position: Int) {
        if (yearsList.size == 0) {
            holder.bind(null)
        } else {
            val viewYearListDate: DatePickerYear = yearsList[position % yearsList.size]
            holder.bind(viewYearListDate)
        }
    }
}