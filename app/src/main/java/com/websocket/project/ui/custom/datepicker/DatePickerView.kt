package com.websocket.project.ui.custom.datepicker

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.websocket.project.R
import java.util.*

class DatePickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), DayAdapterListener, MonthAdapterListener,
    YearAdapterListener {

    private val monthSnapHelper = LinearSnapHelper()
    private val yearSnapHelper = LinearSnapHelper()
    private val daySnapHelper = LinearSnapHelper()
    private var monthSnapPosition = RecyclerView.NO_POSITION
    private var yearSnapPosition = RecyclerView.NO_POSITION
    private var daySnapPosition = RecyclerView.NO_POSITION

    var monthChosen: DatePickerMonth = DatePickerMonth.JANUARY
    var yearChosen: DatePickerYear = DatePickerYear(1982)
    var dayChosen: DatePickerDay = DatePickerDay(1)

    private lateinit var viewDatePickerDayRecycler: RecyclerView
    private lateinit var viewDatePickerMonthRecycler: RecyclerView
    private lateinit var viewDatePickerYearRecycler: RecyclerView

    private var daysInYearsMonth = 0

    private val datePickerMonthAdapter: MonthAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MonthAdapter(this)
    }

    private val datePickerYearAdapter: YearAdapter by lazy(LazyThreadSafetyMode.NONE) {
        YearAdapter(this)
    }

    private val datePickerDayAdapter: DayAdapter by lazy(LazyThreadSafetyMode.NONE) {
        DayAdapter(this)
    }

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.view_date_picker, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.DatePickerView)
        val calendar = Calendar.getInstance()
        val currentYear = calendar[Calendar.YEAR]
        try {
            //MONTH
            val monthsList = mutableListOf<DatePickerMonth>()
            monthsList.addAll(DatePickerMonth.values())

            datePickerMonthAdapter.setData(monthsList)

            val monthLayoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            viewDatePickerMonthRecycler = findViewById(R.id.datePickerMonthRecycler)
            viewDatePickerMonthRecycler.adapter = datePickerMonthAdapter

            val monthViewHolder: RecyclerView.ViewHolder? =
                viewDatePickerMonthRecycler.adapter?.createViewHolder(
                    viewDatePickerMonthRecycler,
                    0
                )

            if (monthViewHolder != null) {
                viewDatePickerMonthRecycler.layoutManager
                    ?.measureChildWithMargins(monthViewHolder.itemView, 0, 0)

                viewDatePickerMonthRecycler.layoutParams.height =
                    monthViewHolder.itemView.measuredHeight * 5
                viewDatePickerMonthRecycler.recycledViewPool.putRecycledView(monthViewHolder)
            }

            monthSnapHelper.attachToRecyclerView(viewDatePickerMonthRecycler)
            viewDatePickerMonthRecycler.scrollToPosition(Integer.MAX_VALUE / 2 - 5) //Scroll to January

            val monthSnapView = monthSnapHelper.findSnapView(monthLayoutManager)
            if (monthSnapView != null) {
                monthSnapPosition = monthLayoutManager.getPosition(monthSnapView)
            }
            viewDatePickerMonthRecycler.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    notifyMonthSnapPositionChange(recyclerView)
                }
            })

            viewDatePickerMonthRecycler.setHasFixedSize(true)

            //YEAR
            val yearsOffset = 30
            val yearsList = mutableListOf<DatePickerYear>()
            val yearStart = currentYear - yearsOffset
            yearChosen.number = yearStart
            for (year in yearStart..currentYear) {
                yearsList.add(DatePickerYear(year))
            }

            datePickerYearAdapter.setData(yearStart, yearsList)

            val yearLayoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )

            viewDatePickerYearRecycler = findViewById(R.id.datePickerYearRecycler)
            viewDatePickerYearRecycler.adapter = datePickerYearAdapter

            val yearViewHolder: RecyclerView.ViewHolder? =
                viewDatePickerYearRecycler.adapter?.createViewHolder(viewDatePickerYearRecycler, 0)

            if (yearViewHolder != null) {
                viewDatePickerYearRecycler.layoutManager
                    ?.measureChildWithMargins(yearViewHolder.itemView, 0, 0)

                viewDatePickerYearRecycler.layoutParams.height =
                    yearViewHolder.itemView.measuredHeight * 5
                viewDatePickerYearRecycler.recycledViewPool.putRecycledView(yearViewHolder)
            }

            yearSnapHelper.attachToRecyclerView(viewDatePickerYearRecycler)
            viewDatePickerYearRecycler.scrollToPosition(Integer.MAX_VALUE / 2 + yearsOffset - 1)

            val yearSnapView = yearSnapHelper.findSnapView(yearLayoutManager)
            if (yearSnapView != null) {
                yearSnapPosition = yearLayoutManager.getPosition(yearSnapView)
            }
            viewDatePickerYearRecycler.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    notifyYearSnapPositionChange(recyclerView)
                }
            })

            viewDatePickerYearRecycler.setHasFixedSize(true)

            //DAY
            daysInYearsMonth = calculateCountDaysInMonthOfYear()
            val daysList = mutableListOf<DatePickerDay>()
            for (day in 1..daysInYearsMonth) {
                daysList.add(DatePickerDay(day))
            }

            val dayLayoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )

            viewDatePickerDayRecycler = findViewById(R.id.datePickerDayRecycler)
            viewDatePickerDayRecycler.adapter = datePickerDayAdapter

            val dayViewHolder: RecyclerView.ViewHolder? =
                viewDatePickerDayRecycler.adapter?.createViewHolder(viewDatePickerDayRecycler, 0)

            if (dayViewHolder != null) {
                viewDatePickerDayRecycler.layoutManager
                    ?.measureChildWithMargins(dayViewHolder.itemView, 0, 0)

                viewDatePickerDayRecycler.setPadding(
                    0,
                    dayViewHolder.itemView.measuredHeight * 2,
                    0,
                    dayViewHolder.itemView.measuredHeight * 2
                )

                viewDatePickerDayRecycler.layoutParams.height =
                    dayViewHolder.itemView.measuredHeight * 5
                viewDatePickerDayRecycler.recycledViewPool.putRecycledView(dayViewHolder)
            }

            daySnapHelper.attachToRecyclerView(viewDatePickerDayRecycler)

            val daySnapView = daySnapHelper.findSnapView(dayLayoutManager)
            if (daySnapView != null) {
                daySnapPosition = dayLayoutManager.getPosition(daySnapView)
            }
            viewDatePickerDayRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    notifyDaySnapPositionChange(recyclerView)
                }
            })

            viewDatePickerDayRecycler.setHasFixedSize(true)
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
        } finally {
            ta.recycle()
        }
    }

    private fun notifyMonthSnapPositionChange(recyclerView: RecyclerView) {
        val snapPosition = monthSnapHelper.getSnapPosition(recyclerView)
        val snapPositionChanged = monthSnapPosition != snapPosition
        if (snapPositionChanged) {
            monthSnapPosition = snapPosition
            datePickerMonthAdapter.setChosen(snapPosition)
            populateDaysList()
        }
    }

    private fun notifyYearSnapPositionChange(recyclerView: RecyclerView) {
        val snapPosition = yearSnapHelper.getSnapPosition(recyclerView)
        val snapPositionChanged = yearSnapPosition != snapPosition
        if (snapPositionChanged) {
            yearSnapPosition = snapPosition
            datePickerYearAdapter.setChosen(snapPosition)
            populateDaysList()
        }
    }

    private fun notifyDaySnapPositionChange(recyclerView: RecyclerView) {
        val snapPosition = daySnapHelper.getSnapPosition(recyclerView)
        daySnapPosition = snapPosition
        datePickerDayAdapter.setChosen(snapPosition)
    }

    private fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
        val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }

    private fun calculateCountDaysInMonthOfYear(): Int {
        val calendar = Calendar.getInstance()
        calendar.set(yearChosen.number, monthChosen.number - 1, 1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    private fun populateDaysList() {
        daysInYearsMonth = calculateCountDaysInMonthOfYear()
        val daysList = mutableListOf<DatePickerDay>()
        for (day in 1..daysInYearsMonth) {
            daysList.add(
                DatePickerDay(
                    dayNumber = day
                )
            )
        }
        datePickerDayAdapter.setData(daysList)
    }

    override fun setChosenDay(datePickerDay: DatePickerDay, position: Int) {
        dayChosen = datePickerDay
    }

    override fun setChosenMonth(datePickerMonth: DatePickerMonth) {
        monthChosen = datePickerMonth
    }

    override fun setChosenYear(datePickerYear: DatePickerYear) {
        yearChosen = datePickerYear
    }
}