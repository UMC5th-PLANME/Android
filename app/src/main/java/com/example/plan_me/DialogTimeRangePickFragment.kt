package com.example.plan_me

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.plan_me.databinding.FragmentDialogAlarmBinding
import com.example.plan_me.databinding.FragmentDialogRepeatBinding
import com.example.plan_me.databinding.FragmentDialogTimepickBinding
import com.example.plan_me.databinding.FragmentDialogTimerangepickBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DialogTimeRangePickFragment(context : Context, dialogTimeRangePickInerface: DialogTimeRangePickInerface):Dialog(context){
    private lateinit var binding : FragmentDialogTimerangepickBinding
    private lateinit var dialogTimeRangePickInerface : DialogTimeRangePickInerface
    private var startTime: String = ""
    private var endTime: String = ""
    init {
        this.dialogTimeRangePickInerface = dialogTimeRangePickInerface
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogTimerangepickBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        clickListener()
    }
    private fun clickListener() {
        binding.dialogTimerangepickCancel.setOnClickListener {
            dialogTimeRangePickInerface?.onRangeClickCancel() // 여기에 선택된 값을 전달
        }
        binding.dialogTimerangepickConfirm.setOnClickListener{
            dialogTimeRangePickInerface?.onRangeClickConfirm(startTime, endTime)
        }
        binding.dialogTimepickStart.setOnTimeChangedListener { view, hourOfDay, minute ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            startTime = SimpleDateFormat("a hh:mm", Locale.getDefault()).format(calendar.time)
            Log.d("time",startTime)
        }
        binding.dialogTimepickEnd.setOnTimeChangedListener { view, hourOfDay, minute ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            endTime = SimpleDateFormat("a hh:mm", Locale.getDefault()).format(calendar.time)
            Log.d("time",endTime)
        }
    }
}