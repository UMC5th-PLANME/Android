package com.example.plan_me.ui.all

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.plan_me.R
import com.example.plan_me.databinding.CalendarWeekDayLayoutBinding
import com.example.plan_me.databinding.FragmentDailyBinding
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

//클릭 이벤트 및 ui수정 필요
class DailyFragment : Fragment() {
    private lateinit var binding: FragmentDailyBinding
    private val currentMonth = YearMonth.now()
    private val currentWeek = LocalDate.now()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDailyBinding.inflate(layoutInflater)

        initDayCalendar()
        clickListener()

        return binding.root
    }

    private fun clickListener() {
        binding.dailyToday.setOnClickListener {
            binding.weekCalendarView.smoothScrollToWeek(currentWeek)
            binding.dailyDate.text = currentWeek.year.toString()+"."+currentWeek.monthValue+"월"
        }
    }
    private fun initDayCalendar() {
        binding.weekCalendarView.dayBinder = object : WeekDayBinder<WeekDayViewContainer> {
            override fun bind(container: WeekDayViewContainer, data: WeekDay) {
                Log.d("data", data.date.toString())
                container.day.weekCalendarDayText.text = data.date.dayOfMonth.toString()
                container.day.weekCalendarDay.text = data.date.dayOfWeek.toString().substring(0,3)
            }

            override fun create(view: View) = WeekDayViewContainer(view)
        }
        val week_currentDate = LocalDate.now()
        val week_startDate = currentMonth.minusMonths(100).atStartOfMonth() // Adjust as needed
        val week_endDate = currentMonth.plusMonths(100).atEndOfMonth()  // Adjust as needed
        val week_daysOfWeek = daysOfWeek()
        Log.d("days", week_daysOfWeek.toString())
        binding.weekCalendarView.setup(week_startDate, week_endDate, week_daysOfWeek.first())
        binding.weekCalendarView.scrollToWeek(week_currentDate)
        binding.weekCalendarView.weekScrollListener = {
            val year = it.days.first().date.year.toString()
            val month = it.days.first().date.monthValue
            binding.dailyDate.text = year+"."+month+"월"
        }
    }
    inner class WeekDayViewContainer(view: View): ViewContainer(view) {
        val day = CalendarWeekDayLayoutBinding.bind(view)
    }
}