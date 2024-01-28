package com.example.plan_me.ui.all.Daily

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.R
import com.example.plan_me.databinding.CalendarWeekDayLayoutBinding
import com.example.plan_me.databinding.FragmentDailyBinding
import com.example.plan_me.data.local.entity.category
import com.example.plan_me.data.local.entity.schedule
import com.example.plan_me.ui.dialog.DialogDailyCalenderFragment
import com.example.plan_me.ui.dialog.DialogDailyCalenderInterface
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import java.time.LocalDate
import java.time.YearMonth

//클릭 이벤트 및 ui수정 필요
class DailyFragment : Fragment(), DialogDailyCalenderInterface {
    private lateinit var binding: FragmentDailyBinding
    private val currentMonth = YearMonth.now()
    private val currentWeek = LocalDate.now()
    private lateinit var dialogDailyCalenderFragment :DialogDailyCalenderFragment

    //예제 데이터
    lateinit var study : category
    lateinit var exercise : category

    private  var cate : ArrayList<category> = ArrayList()

    private val sche : ArrayList<schedule> = ArrayList()

    private val s1 : schedule = schedule(0, false, "웹프 6-8강 복습", LocalDate.of(2024, 1, 23))
    private val s2 : schedule = schedule(1, false, "축구하기", LocalDate.of(2024, 1, 29))
    private val s3 : schedule = schedule(1, false, "축구하기", LocalDate.of(2024, 1, 23))
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDailyBinding.inflate(layoutInflater)
        //예제 데이터
        study = category(0, "📄 STUDY", R.color.lemon)
        exercise = category(1, "\uD83D\uDCAA Exercise", R.color.sky_blue)
        cate.add(study)
        cate.add(exercise)
        sche.add(s1)
        sche.add(s2)
        sche.add(s3)

        initDayCalendar()
        clickListener()

        val dailyRVAdapter = DailyRVAdapter(cate, sche)
        binding.dailyScheduleList.adapter = dailyRVAdapter
        binding.dailyScheduleList.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    private fun clickListener() {
        binding.dailyToday.setOnClickListener {
            binding.weekCalendarView.smoothScrollToWeek(currentWeek)
            binding.dailyDate.text = currentWeek.year.toString()+"."+currentWeek.monthValue+"월"
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
        var isSelcted = false
        init {
            view.setOnClickListener {
                if (isSelcted) {
                    day.weekDayLayout.setBackgroundResource(R.color.transparent)
                    isSelcted = false
                }
                else {
                    day.weekDayLayout.setBackgroundResource(R.drawable.calendar_daily_circle_selected)
                    isSelcted = true
                }
            }
        }
    }

    override fun onClickCalender(date: LocalDate?) {
        binding.weekCalendarView.scrollToWeek(date!!)
        binding.dailyDate.text = currentWeek.year.toString()+"."+currentWeek.monthValue+"월"
    }
}