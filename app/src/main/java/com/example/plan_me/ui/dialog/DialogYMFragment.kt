package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.example.plan_me.databinding.FragmentDialogYaerMonthPickerBinding
import java.time.Year
import java.time.YearMonth

class DialogYMFragment(context : Context, private val yearMonth: YearMonth, dialogYMPickInerface: DialogYMPickInerface): Dialog(context) {
    private lateinit var binding: FragmentDialogYaerMonthPickerBinding

    private val monthArr = arrayOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월")
    lateinit var yearArr : Array<String>

    private var month: String? = null
    private var year: String? = null

    private lateinit var dialogYMPickInerface : DialogYMPickInerface
    init {
        this.dialogYMPickInerface = dialogYMPickInerface
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogYaerMonthPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initPicker(yearMonth)
        clickListener()
    }
    private fun initPicker(yearMonth: YearMonth) {
        val currentYear = Year.now().value
        val startYear = currentYear - 10
        val endYear = currentYear + 10
        yearArr = (startYear..endYear).map { String.format("%04d년", it) }.toTypedArray()
        val initialYearValue = yearArr.indexOf(yearMonth.year.toString() +"년")
        val initialMonthValue = monthArr.indexOf(yearMonth.month.value.toString() +"월")
        binding.dialogYearPicker.let {
            it.displayedValues = yearArr
            it.minValue= 0
            it.maxValue= yearArr.size -1
            it.value=initialYearValue
        }
        binding.dialogMonthPicker.let {
            it.displayedValues = monthArr
            it.minValue= 0
            it.maxValue= monthArr.size -1
            it.value=initialMonthValue
        }
    }

    private fun clickListener() {
        binding.dialogYmCancel.setOnClickListener {
            dismiss()
        }
        binding.dialogYmConfirm.setOnClickListener {
            month = monthArr.get(binding.dialogMonthPicker.value)
            year = yearArr.get(binding.dialogYearPicker.value)
            Log.d("year", month.toString())
            Log.d("month", year.toString())
            dialogYMPickInerface.onClickConfirm(year, month)
        }
    }
}