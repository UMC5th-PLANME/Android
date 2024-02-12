package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.example.plan_me.R
import com.example.plan_me.databinding.DialogCalendarDayLayoutBinding
import com.example.plan_me.databinding.FragmentDialogCalendarBinding
import com.example.plan_me.ui.all.Monthly.MonthViewContainer
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

class DialogCalenderFragment(context : Context, dialogCalenderInterface: DialogCalenderInterface):Dialog(context){
    private lateinit var binding : FragmentDialogCalendarBinding
    private var selectedStartDate: LocalDate? = null
    private var selectedEndDate: LocalDate? = null
    private var rangeRe : Boolean = false
    var pageMonth = YearMonth.now()


    private val currentMonth = YearMonth.now()
    private lateinit var startMonth :YearMonth
    private lateinit var endMonth :YearMonth
    private lateinit var firstDayOfWeek : DayOfWeek


    private var dialogCalenderInterface : DialogCalenderInterface

    init {
        this.dialogCalenderInterface = dialogCalenderInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        startMonth = currentMonth.minusMonths(100)  // Adjust as needed
        endMonth = currentMonth.plusMonths(100)  // Adjust as needed
        firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library

        binding.dialogCalenderCalendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()

                if (data.position == DayPosition.MonthDate) {
                    container.textView.setTextColor(Color.BLACK)
                } else {
                    container.textView.setTextColor(Color.GRAY)
                    container.canClick = false
                }

                if (container.canClick) {
                    container.textView.setOnClickListener {
                        onDaySelected(data.date)
                        container.textView.setBackgroundResource(R.drawable.calender_onclick_circle)
                    }
                }

                if (rangeRe) {
                    Log.d("true", "true")
                    container.textView.background = null
                    if (data.date == selectedStartDate) { //선택한 날짜와 현재 날짜가 같고, 끝라인 리아면
                        container.textView.setBackgroundResource(R.drawable.calender_onclick_circle)
                    }
                }
                else{
                    if (isDateInRange(data.date)) {
                        container.textView.setBackgroundResource(R.drawable.calender_box_2)
                    } else if (data.date == selectedStartDate) {
                        // 다른 날짜에 대한 배경 재설정
                        container.textView.setBackgroundResource(R.drawable.calendar_start)
                    } else if (data.date == selectedEndDate) {
                        // 다른 날짜에 대한 배경 재설정
                        container.textView.setBackgroundResource(R.drawable.calendar_end)
                    } else {
                        container.textView.background = null
                    }
                }
            }

            override fun create(view: View): DayViewContainer {
                Log.d("pageMonth", pageMonth.toString())
                return DayViewContainer(view)
            }  // this refers to DaySelectionListener
        }
        binding.dialogCalenderCalendarView.setup(startMonth, endMonth, firstDayOfWeek)
        binding.dialogCalenderCalendarView.scrollToMonth(currentMonth)

        val daysOfWeek = daysOfWeek()

        val titlesContainer = findViewById<ViewGroup>(R.id.dialog_calender_titlesContainer)
        titlesContainer.children
            .map {it as TextView }
            .forEachIndexed {index, textView ->
                val dayOfWeek = daysOfWeek[index]
                val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                textView.text = title
            }

        binding.dialogCalenderCalendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
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
        clickListener()
    }
    private fun clickListener() {

        binding.dialogCalenderCalendarView.monthScrollListener = { calendarMonth ->
            pageMonth = calendarMonth.yearMonth
            val year = pageMonth.year.toString()
            val month = pageMonth.month.value
            binding.dialogCalenderDialogDate.text = year +"." + month + "월"
        }
        binding.dialogCalenderDialogRight.setOnClickListener {
            val nextMonth = pageMonth.nextMonth
            pageMonth = pageMonth.nextMonth
            Log.d("month", pageMonth.toString())
            binding.dialogCalenderCalendarView.smoothScrollToMonth(nextMonth)
        }
        binding.dialogCalenderDialogLeft.setOnClickListener {
            val preMonth = pageMonth.previousMonth
            pageMonth = pageMonth.previousMonth
            Log.d("month", pageMonth.toString())
            binding.dialogCalenderCalendarView.smoothScrollToMonth(preMonth)
        }
        binding.dialogCalenderDialogToday.setOnClickListener{
            binding.dialogCalenderCalendarView.smoothScrollToMonth(currentMonth)
            pageMonth = currentMonth
        }
        binding.dialogCalenderCancel.setOnClickListener {
            dismiss()
        }
        binding.dialogCalenderConfirm.setOnClickListener {
            if (selectedEndDate == null) selectedEndDate = selectedStartDate
            dialogCalenderInterface!!.onClickCalenderConfirm(selectedStartDate, selectedEndDate)
        }
    }

    private fun onDaySelected(date: LocalDate) {
        rangeRe=false
        if (selectedStartDate == null) {
            // 시작 날짜를 선택한 경우
            selectedStartDate = date
            selectedEndDate = null
        } else if (selectedEndDate == null && selectedStartDate != null && date == selectedStartDate) {
            // 다음에 지정해야하는데 같은 날짜 지정
            selectedStartDate = date
        }else if (selectedEndDate == null && selectedStartDate != null) {
            // 두번째 날짜 선택
            if (date.isAfter(selectedStartDate)) {
                selectedEndDate = date
            }else {
                selectedEndDate = selectedStartDate
                selectedStartDate = date
            }
            binding.dialogCalenderCalendarView.notifyCalendarChanged()
        }else if (selectedEndDate != null && selectedStartDate != null) {
            // 다시 시작 날짜부터 선택한 경우
            selectedStartDate = date
            selectedEndDate = null
            rangeRe=true
            binding.dialogCalenderCalendarView.notifyCalendarChanged()
        }
        Log.d("Date start", selectedStartDate.toString())
        Log.d("Date end", selectedEndDate.toString())
    }

    private fun isDateInRange(date: LocalDate): Boolean {
        return selectedStartDate != null && selectedEndDate != null &&
                (date.isAfter(selectedStartDate) && date.isBefore(selectedEndDate))
    }

    inner class DayViewContainer(view: View): ViewContainer(view) {
        val textView = DialogCalendarDayLayoutBinding.bind(view).calendarDayText
        var canClick : Boolean = true

    }
}