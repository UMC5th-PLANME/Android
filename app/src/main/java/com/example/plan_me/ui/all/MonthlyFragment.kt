package com.example.plan_me.ui.all

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.plan_me.R
import com.example.plan_me.databinding.CalendarDayLayoutBinding
import com.example.plan_me.databinding.FragmentMonthlyBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

//새로운 클릭 리스너 구현해야함
class MonthlyFragment: Fragment() {
    private lateinit var binding: FragmentMonthlyBinding

    var pageMonth = YearMonth.now()

    private val currentMonth = YearMonth.now()
    private lateinit var startMonth :YearMonth
    private lateinit var endMonth :YearMonth
    private lateinit var firstDayOfWeek :DayOfWeek
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMonthlyBinding.inflate(layoutInflater)

        clickListener()
        initCalendar()

        return binding.root
    }

    private fun initCalendar() {
        startMonth = currentMonth.minusMonths(100)  // Adjust as needed
        endMonth = currentMonth.plusMonths(100)  // Adjust as needed
        firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        binding.monthlyCalendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day.calendarDayText.text = data.date.dayOfMonth.toString()
                if (data.position == DayPosition.MonthDate) {
                    container.day.calendarDayText.setTextColor(Color.BLACK)
                } else {
                    container.day.calendarDayText.setTextColor(Color.LTGRAY)
                    container.canClick = false
                }
            }
            override fun create(view: View): DayViewContainer {
                Log.d("pageMonth", pageMonth.toString())
                return DayViewContainer(view)
            }  // this refers to DaySelectionListener
        }
        binding.monthlyCalendarView.setup(startMonth, endMonth, firstDayOfWeek)
        binding.monthlyCalendarView.scrollToMonth(currentMonth)

        val daysOfWeek = daysOfWeek()

        val titlesContainer = binding.root.findViewById<ViewGroup>(R.id.monthly_titlesContainer)
        Log.d("titlesContainer", titlesContainer.toString())
        titlesContainer.children
            .map {it as TextView }
            .forEachIndexed {index, textView ->
                val dayOfWeek = daysOfWeek[index]
                val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase()
                textView.text = title
            }

        binding.monthlyCalendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                // Remember that the header is reused so this will be called for each month.
                // However, the first day of the week will not change so no need to bind
                // the same view every time it is reused.
                if (container.titlesContainer.tag == null) {
                    container.titlesContainer.tag = data.yearMonth
                    container.titlesContainer.children.map { it as TextView }
                        .forEachIndexed { index, textView ->
                            val dayOfWeek = daysOfWeek[index]
                            val title =
                                dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                            textView.text = title
                            // In the code above, we use the same `daysOfWeek` list
                            // that was created when we set up the calendar.
                            // However, we can also get the `daysOfWeek` list from the month data:
                            // val daysOfWeek = data.weekDays.first().map { it.date.dayOfWeek }
                            // Alternatively, you can get the value for this specific index:
                            // val dayOfWeek = data.weekDays.first()[index].date.dayOfWeek
                        }
                }
            }
        }
    }
    private fun clickListener() {
        binding.monthlyCalendarView.monthScrollListener = { calendarMonth ->
            val pageMonth = calendarMonth.yearMonth
            val year = pageMonth.year.toString()
            val month = pageMonth.month.value
            binding.monthlyDate.text = year +"." + month + "월"
        }
        binding.monthlyRight.setOnClickListener {
            val nextMonth = pageMonth.nextMonth
            pageMonth = pageMonth.nextMonth
            Log.d("month", pageMonth.toString())
            binding.monthlyCalendarView.smoothScrollToMonth(nextMonth)
        }
        binding.monthlyLeft.setOnClickListener {
            val preMonth = pageMonth.previousMonth
            pageMonth = pageMonth.previousMonth
            Log.d("month", pageMonth.toString())
            binding.monthlyCalendarView.smoothScrollToMonth(preMonth)
        }
        binding.monthlyToday.setOnClickListener{
            binding.monthlyCalendarView.smoothScrollToMonth(currentMonth)
            pageMonth = currentMonth
        }
    }
    inner class DayViewContainer(view: View): ViewContainer(view) {
        val day = CalendarDayLayoutBinding.bind(view)
        var isSelected : Boolean = false
        private var selectedDate: LocalDate? = null
        var canClick : Boolean = true
        init {
            view.setOnClickListener {
                if (canClick) {
                    if (!isSelected) {
                        view.setBackgroundResource(R.drawable.calender_onclick_circle)
                        isSelected = true
                        val text = day.calendarDayText.text.toString()
                        val day = text.toInt()
                        selectedDate = pageMonth.atDay(day)
                        Log.d("selected", selectedDate.toString())
                    } else {
                        view.setBackgroundResource(R.color.transparent)
                        isSelected = false
                    }
                }
            }
        }
    }
}