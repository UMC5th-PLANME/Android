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
import com.example.plan_me.databinding.FragmentDialogDailyCalenderBinding
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

class DialogDailyCalenderFragment(context : Context, dialogDailyCalenderInterface: DialogDailyCalenderInterface):Dialog(context){
    private lateinit var binding : FragmentDialogDailyCalenderBinding
    var pageMonth = YearMonth.now()


    private val currentMonth = YearMonth.now()
    private lateinit var startMonth :YearMonth
    private lateinit var endMonth :YearMonth
    private lateinit var firstDayOfWeek : DayOfWeek


    private var dialogDailyCalenderInterface : DialogDailyCalenderInterface

    init {
        this.dialogDailyCalenderInterface = dialogDailyCalenderInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogDailyCalenderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        startMonth = currentMonth.minusMonths(100)  // Adjust as needed
        endMonth = currentMonth.plusMonths(100)  // Adjust as needed
        firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library

        binding.dialogDailyCalenderCalendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
                if (data.position == DayPosition.MonthDate) {
                    container.textView.setTextColor(Color.BLACK)
                } else {
                    container.textView.setTextColor(Color.GRAY)
                    container.canClick = false
                }
            }

            override fun create(view: View): DayViewContainer {
                Log.d("pageMonth", pageMonth.toString())
                return DayViewContainer(view)
            }  // this refers to DaySelectionListener
        }
        binding.dialogDailyCalenderCalendarView.setup(startMonth, endMonth, firstDayOfWeek)
        binding.dialogDailyCalenderCalendarView.scrollToMonth(currentMonth)

        val daysOfWeek = daysOfWeek()

        val titlesContainer = findViewById<ViewGroup>(R.id.dialog_daily_calender_titlesContainer)
        titlesContainer.children
            .map {it as TextView }
            .forEachIndexed {index, textView ->
                val dayOfWeek = daysOfWeek[index]
                val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                textView.text = title
            }

        binding.dialogDailyCalenderCalendarView.monthHeaderBinder = object :
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

        binding.dialogDailyCalenderCalendarView.monthScrollListener = { calendarMonth ->
            val pageMonth = calendarMonth.yearMonth
            val year = pageMonth.year.toString()
            val month = pageMonth.month.value
            binding.dialogDailyCalenderDialogDate.text = year +"." + month + "월"
        }
        binding.dialogDailyCalenderDialogRight.setOnClickListener {
            val nextMonth = pageMonth.nextMonth
            pageMonth = pageMonth.nextMonth
            Log.d("month", pageMonth.toString())
            binding.dialogDailyCalenderCalendarView.smoothScrollToMonth(nextMonth)
        }
        binding.dialogDailyCalenderDialogLeft.setOnClickListener {
            val preMonth = pageMonth.previousMonth
            pageMonth = pageMonth.previousMonth
            Log.d("month", pageMonth.toString())
            binding.dialogDailyCalenderCalendarView.smoothScrollToMonth(preMonth)
        }
        binding.dialogDailyCalenderDialogToday.setOnClickListener{
            binding.dialogDailyCalenderCalendarView.smoothScrollToMonth(currentMonth)
            pageMonth = currentMonth
        }
    }


    inner class DayViewContainer(view: View): ViewContainer(view) {
        val textView = DialogCalendarDayLayoutBinding.bind(view).calendarDayText
        var canClick : Boolean = true
        private var selectedDate: LocalDate? = null
        init {
            view.setOnClickListener {
                if (canClick) {
                    val text = textView.text.toString()
                    val day = text.toInt()
                    selectedDate = pageMonth.atDay(day)
                    dialogDailyCalenderInterface.onClickCalender(selectedDate)
                    dismiss()
                }
            }
        }
    }
}