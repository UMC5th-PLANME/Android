package com.example.plan_me.ui.all.Daily

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.R
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.schedule.AllScheduleRes
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.service.schedule.ScheduleService
import com.example.plan_me.data.remote.view.category.AllCategoryView
import com.example.plan_me.data.remote.view.schedule.AllScheduleView
import com.example.plan_me.databinding.CalendarWeekDayLayoutBinding
import com.example.plan_me.databinding.FragmentDailyBinding
import com.example.plan_me.ui.dialog.DialogDailyCalenderFragment
import com.example.plan_me.ui.dialog.DialogDailyCalenderInterface
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import kotlinx.coroutines.selects.select
import java.time.LocalDate
import java.time.YearMonth

class DailyFragment : Fragment(),
    DialogDailyCalenderInterface,
    AllScheduleView,
    AllCategoryView{
    private lateinit var binding: FragmentDailyBinding
    private val currentMonth = YearMonth.now()
    private val currentWeek = LocalDate.now()
    private lateinit var dialogDailyCalenderFragment :DialogDailyCalenderFragment
    private lateinit var categoryList : List<CategoryList>
    private lateinit var scheduleList : List<ScheduleList>
    val groupedSchedules = mutableMapOf<Int, MutableList<ScheduleList>>()

    private lateinit var selectDate: LocalDate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDailyBinding.inflate(layoutInflater)
        selectDate = currentWeek


        getCategoryList()
        initDayCalendar()
        clickListener()

        return binding.root
    }

    private fun clickListener() {
        binding.dailyToday.setOnClickListener {
            binding.weekCalendarView.smoothScrollToWeek(currentWeek)
            binding.dailyDate.text = currentWeek.year.toString()+"."+currentWeek.monthValue+"월"
            selectDate = currentWeek

            binding.weekCalendarView.notifyCalendarChanged()
            filteringSchedule()
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
                }else {
                    container.day.weekDayLayout.setBackgroundResource(R.color.transparent)
                }

                container.day.weekDayLayout.setOnClickListener {
                    selectDate = data.date
                    Log.d("selected", selectDate.toString())
                    container.day.weekDayLayout.setBackgroundResource(R.drawable.calendar_daily_circle_selected)
                    binding.weekCalendarView.notifyCalendarChanged()

                    filteringSchedule()
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

    private fun getScheduleAll() {
        val access_token = "Bearer " + requireActivity().getSharedPreferences("getRes",
            AppCompatActivity.MODE_PRIVATE
        ).getString("getAccessToken", "")
        val scheduleService = ScheduleService()
        scheduleService.setAllScheduleView(this)
        scheduleService.getScheduleAllFun(access_token)
    }

    private fun getCategoryList() {
        val access_token = "Bearer " + requireActivity().getSharedPreferences("getRes",
            AppCompatActivity.MODE_PRIVATE
        ).getString("getAccessToken", "")
        val setCategoryService = CategoryService()
        setCategoryService.setAllCategoryView(this)
        setCategoryService.getCategoryAllFun(access_token!!)
    }

    private fun filteringSchedule() {
        // categoryId를 기준으로 ScheduleList를 그룹화하여 Schedule_filter 객체로 만듦
        // 날짜까지 확인
        groupedSchedules.clear()
        for (schedule in scheduleList) {
            val categoryId = schedule.category_id
            val startDate = LocalDate.parse(schedule.startDate)
            val endDate = LocalDate.parse(schedule.endDate)

            if (selectDate.isEqual(startDate) || selectDate.isEqual(endDate) ||
                (selectDate.isAfter(startDate) && selectDate.isBefore(endDate))) {
                if (!groupedSchedules.containsKey(categoryId)) {
                    groupedSchedules[categoryId] = mutableListOf()
                }
                groupedSchedules[categoryId]?.add(schedule)
            }
        }
        Log.d("groupedSchedule", groupedSchedules.toString())
    }

    private fun filteringCategory(): List<CategoryList> {
        val filteringCategory = mutableListOf<CategoryList>()

        for ((categoryId, _) in groupedSchedules) {
            val category = categoryList.find { it.categoryId == categoryId }
            category?.let {
                filteringCategory.add(it)
            }
        }

        return filteringCategory
    }

    private fun initRV() {
        val dailyRVAdapter = DailyRVAdapter(filteringCategory(), groupedSchedules, requireContext())
        binding.dailyScheduleList.adapter = dailyRVAdapter
        binding.dailyScheduleList.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onClickCalender(date: LocalDate?) {
        binding.weekCalendarView.scrollToWeek(date!!)
        binding.dailyDate.text = currentWeek.year.toString()+"."+currentWeek.monthValue+"월"
        selectDate = date

        binding.weekCalendarView.notifyCalendarChanged()
        filteringSchedule()
        initRV()
    }

    override fun onAllCategorySuccess(response: AllCategoryRes) {
        categoryList = response.result.categoryList
        getScheduleAll()
    }

    override fun onAllCategoryFailure(response: AllCategoryRes) {
        TODO("Not yet implemented")
    }

    override fun onAllScheduleSuccess(response: AllScheduleRes) {
        scheduleList = response.result.scheduleList

        filteringSchedule()
        initRV()
    }

    override fun onAllScheduleFailure(response: AllScheduleRes) {
        TODO("Not yet implemented")
    }


}