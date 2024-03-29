package com.example.plan_me.ui.all.Daily

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.R
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.databinding.CalendarWeekDayLayoutBinding
import com.example.plan_me.databinding.FragmentDailyBinding
import com.example.plan_me.ui.dialog.DialogDailyCalenderFragment
import com.example.plan_me.ui.dialog.DialogDailyCalenderInterface
import com.example.plan_me.utils.viewModel.CalendarViewModel
import com.example.plan_me.utils.viewModel.CalendarViewModelFactory
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import java.time.LocalDate
import java.time.YearMonth

class DailyFragment : Fragment(),
    DialogDailyCalenderInterface,
    ScheduleRVAdapter.SendSignalModify{
    private lateinit var binding: FragmentDailyBinding
    private val currentMonth = YearMonth.now()
    private val currentWeek = LocalDate.now()
    private lateinit var dialogDailyCalenderFragment :DialogDailyCalenderFragment
    var groupedSchedules = mutableMapOf<Int, MutableList<ScheduleList>>()

    private lateinit var calendarViewModel: CalendarViewModel

    private lateinit var selectDate: LocalDate


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDailyBinding.inflate(layoutInflater)
        val factory = CalendarViewModelFactory(requireActivity().getSharedPreferences("getRes", Context.MODE_PRIVATE))
        calendarViewModel = ViewModelProvider(requireActivity(), factory).get(CalendarViewModel::class.java)

        calendarViewModel._scheduleList.observe(viewLifecycleOwner, Observer {
            groupedSchedules = calendarViewModel.filteringSchedule(currentWeek, groupedSchedules)
            initRV()
        })


        selectDate = currentWeek
        initDayCalendar()
        clickListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        calendarViewModel.getCategoryList()
    }

    private fun clickListener() {
        binding.dailyToday.setOnClickListener {
            binding.weekCalendarView.smoothScrollToWeek(currentWeek)
            binding.dailyDate.text = currentWeek.year.toString()+"."+currentWeek.monthValue+"월"
            selectDate = currentWeek

            binding.weekCalendarView.notifyCalendarChanged()
            groupedSchedules = calendarViewModel.filteringSchedule(currentWeek, groupedSchedules)
            initRV()
        }
        binding.dailyDialogCalendar.setOnClickListener {
            dialogDailyCalenderFragment = DialogDailyCalenderFragment(requireContext(), this)
            dialogDailyCalenderFragment.show()
        }
    }
    private fun initDayCalendar() {
        binding.weekCalendarView.dayBinder = object : WeekDayBinder<WeekDayViewContainer> {
            override fun bind(container: WeekDayViewContainer, data: WeekDay) {
                Log.d("data", data.date.toString())
                if (selectDate == data.date) {
                    container.day.weekDayLayout.setBackgroundResource(R.drawable.calendar_daily_circle_selected)
                    groupedSchedules = calendarViewModel.filteringSchedule(data.date, groupedSchedules)
                    initRV()
                }else {
                    container.day.weekDayLayout.setBackgroundResource(R.color.transparent)
                }

                container.day.weekDayLayout.setOnClickListener {
                    selectDate = data.date
                    Log.d("selected", selectDate.toString())
                    container.day.weekDayLayout.setBackgroundResource(R.drawable.calendar_daily_circle_selected)
                    binding.weekCalendarView.notifyCalendarChanged()
                    groupedSchedules = calendarViewModel.filteringSchedule(data.date, groupedSchedules)
                    initRV()
                }
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




    private fun initRV() {
        val dailyRVAdapter = DailyRVAdapter(calendarViewModel.filteringCategory(groupedSchedules), groupedSchedules, requireContext(), this)
        binding.dailyScheduleList.adapter = dailyRVAdapter
        binding.dailyScheduleList.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onClickCalender(date: LocalDate?) {
        binding.weekCalendarView.scrollToWeek(date!!)
        binding.dailyDate.text = currentWeek.year.toString()+"."+currentWeek.monthValue+"월"
        selectDate = date

        binding.weekCalendarView.notifyCalendarChanged()
        groupedSchedules = calendarViewModel.filteringSchedule(date, groupedSchedules)
        initRV()
    }

    override fun sendSignalModify() {
        calendarViewModel.getCategoryList()
    }


}