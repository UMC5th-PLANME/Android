package com.example.plan_me.ui.all.Monthly

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.plan_me.R
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.schedule.AllScheduleRes
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.service.schedule.ScheduleService
import com.example.plan_me.data.remote.view.category.AllCategoryView
import com.example.plan_me.data.remote.view.schedule.AllScheduleView
import com.example.plan_me.databinding.CalendarDayLayoutBinding
import com.example.plan_me.databinding.FragmentMonthlyBinding
import com.example.plan_me.ui.all.Daily.ScheduleRVAdapter
import com.example.plan_me.ui.dialog.DialogCalendarBtmFragment
import com.example.plan_me.ui.dialog.DialogYMFragment
import com.example.plan_me.ui.dialog.DialogYMPickInerface
import com.example.plan_me.utils.viewModel.CalendarViewModel
import com.example.plan_me.utils.viewModel.CalendarViewModelFactory
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
class MonthlyFragment: Fragment(),
    DialogYMPickInerface,
    ScheduleRVAdapter.SendSignalModify{
    private lateinit var binding: FragmentMonthlyBinding
    private lateinit var dialogYMFragment: DialogYMFragment

    var pageMonth = YearMonth.now()

    private val currentMonth = YearMonth.now()
    private lateinit var startMonth :YearMonth
    private lateinit var endMonth :YearMonth
    private lateinit var firstDayOfWeek :DayOfWeek

    var groupedSchedules = mutableMapOf<Int, MutableList<ScheduleList>>()

    private lateinit var calendarViewModel: CalendarViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMonthlyBinding.inflate(layoutInflater)
        val factory = CalendarViewModelFactory(requireActivity().getSharedPreferences("getRes", Context.MODE_PRIVATE))
        calendarViewModel = ViewModelProvider(requireActivity(), factory).get(CalendarViewModel::class.java)

        initCalendar()
        clickListener()

        return binding.root
    }


    private fun initCalendar() {
        startMonth = currentMonth.minusMonths(100)  // Adjust as needed
        endMonth = currentMonth.plusMonths(100)  // Adjust as needed
        firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        binding.monthlyCalendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day.calendarDayText.text = data.date.dayOfMonth.toString()

                container.day.calendarDayIndicator1.visibility = View.GONE
                container.day.calendarDayIndicator2.visibility = View.GONE
                container.day.calendarDayIndicator3.visibility = View.GONE
                container.day.calendarDayIndicator4.visibility = View.GONE
                Log.d("data", data.toString())

                if (data.position == DayPosition.MonthDate) {
                    container.day.calendarDayText.setTextColor(Color.BLACK)
                } else {
                    container.day.calendarDayText.setTextColor(Color.LTGRAY)
                    container.canClick = false
                }

                groupedSchedules = calendarViewModel.filteringSchedule(data.date, groupedSchedules)
                val categoryList = calendarViewModel.filteringCategory(groupedSchedules)

                container.day.monthyDayLayout.setOnClickListener {
                    if (container.canClick) {
                        groupedSchedules = calendarViewModel.filteringSchedule(data.date, groupedSchedules)
                        val categoryList = calendarViewModel.filteringCategory(groupedSchedules)
                        Log.d("filter", categoryList.toString())
                        Log.d("filter", groupedSchedules.toString())
                        val btmSheet = DialogCalendarBtmFragment(data.date, requireContext(), this@MonthlyFragment)
                        btmSheet.show(parentFragmentManager, btmSheet.tag)
                    }
                }

                if(groupedSchedules.isNotEmpty() && categoryList.isNotEmpty()) {
                    if (groupedSchedules.size == 1) {
                        container.day.calendarDayIndicator1.visibility = View.VISIBLE
                        container.day.calendarDayIndicator1.setBackgroundResource(categoryList[0].color)
                    } else if (groupedSchedules.size == 2) {
                        container.day.calendarDayIndicator1.visibility = View.VISIBLE
                        container.day.calendarDayIndicator1.setBackgroundResource(categoryList[0].color)
                        container.day.calendarDayIndicator2.visibility = View.VISIBLE
                        container.day.calendarDayIndicator2.setBackgroundResource(categoryList[1].color)

                    } else if (groupedSchedules.size == 3) {
                        container.day.calendarDayIndicator1.visibility = View.VISIBLE
                        container.day.calendarDayIndicator1.setBackgroundResource(categoryList[0].color)
                        container.day.calendarDayIndicator2.visibility = View.VISIBLE
                        container.day.calendarDayIndicator2.setBackgroundResource(categoryList[1].color)
                        container.day.calendarDayIndicator3.visibility = View.VISIBLE
                        container.day.calendarDayIndicator3.setBackgroundResource(categoryList[2].color)

                    } else {
                        container.day.calendarDayIndicator1.visibility = View.VISIBLE
                        container.day.calendarDayIndicator1.setBackgroundResource(categoryList[0].color)
                        container.day.calendarDayIndicator2.visibility = View.VISIBLE
                        container.day.calendarDayIndicator2.setBackgroundResource(categoryList[1].color)
                        container.day.calendarDayIndicator3.visibility = View.VISIBLE
                        container.day.calendarDayIndicator3.setBackgroundResource(categoryList[2].color)
                        container.day.calendarDayIndicator4.visibility = View.VISIBLE
                        container.day.calendarDayIndicator4.setBackgroundResource(categoryList[3].color)

                    }
                }
            }
            override fun create(view: View): DayViewContainer {
                Log.d("pageMonth", pageMonth.toString())
                return DayViewContainer(view)
            }  // this refers to DaySelectionListener
        }
        binding.monthlyCalendarView.setup(startMonth, endMonth, firstDayOfWeek)
        binding.monthlyCalendarView.scrollToMonth(pageMonth)

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

    override fun onResume() {
        super.onResume()
        calendarViewModel.getCategoryList()
    }
    private fun clickListener() {
        calendarViewModel._categoryList.observe(viewLifecycleOwner, Observer {
            binding.monthlyCalendarView.notifyCalendarChanged()
        })
        calendarViewModel._scheduleList.observe(viewLifecycleOwner, Observer {
            binding.monthlyCalendarView.notifyCalendarChanged()
        })
        binding.monthlyCalendarView.monthScrollListener = { calendarMonth ->
            pageMonth = calendarMonth.yearMonth
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
        binding.monthlyDate.setOnClickListener {
            dialogYMFragment = DialogYMFragment(requireContext(), pageMonth, this)
            dialogYMFragment.show()
        }
    }
    inner class DayViewContainer(view: View): ViewContainer(view) {
        val day = CalendarDayLayoutBinding.bind(view)
        var canClick : Boolean = true
        }

    override fun onClickConfirm(year: String?, month: String?) {
        val monthDigitsOnly = month!!.replace("\\D".toRegex(), "").toInt()
        val yearDigitsOnly = year!!.replace("\\D".toRegex(), "").toInt()
        pageMonth = YearMonth.of(yearDigitsOnly, monthDigitsOnly)
        Log.d("new pageMonth", pageMonth.toString())
        binding.monthlyCalendarView.scrollToMonth(pageMonth)
        binding.monthlyDate.text = yearDigitsOnly.toString() + "." + month.toString()+"월"
        dialogYMFragment.dismiss()
    }

    override fun sendSignalModify() {
        calendarViewModel.getCategoryList()
    }

}
