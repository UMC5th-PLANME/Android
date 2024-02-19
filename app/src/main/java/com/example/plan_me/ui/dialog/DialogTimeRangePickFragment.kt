package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.example.plan_me.databinding.FragmentDialogTimerangepickBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DialogTimeRangePickFragment(context : Context, dialogTimeRangePickInerface: DialogTimeRangePickInerface):Dialog(context){
    private lateinit var binding : FragmentDialogTimerangepickBinding
    private lateinit var dialogTimeRangePickInerface : DialogTimeRangePickInerface
    private var startTime: String = ""
    private var endTime: String = ""
    private var isChecked : Boolean = false
    init {
        this.dialogTimeRangePickInerface = dialogTimeRangePickInerface
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogTimerangepickBinding.inflate(layoutInflater)
        val calendar = Calendar.getInstance()
        startTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
        endTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        clickListener()
    }
    private fun clickListener() {
        binding.dialogTimerangepickCancel.setOnClickListener {
            dialogTimeRangePickInerface?.onRangeClickCancel() // 여기에 선택된 값을 전달
        }
        binding.dialogTimerangepickConfirm.setOnClickListener{
            if (isChecked) {
                startTime = "00:00"
                endTime = "23:59"
            }
            dialogTimeRangePickInerface?.onRangeClickConfirm(startTime, endTime)
        }
        binding.dialogTimepickStart.setOnTimeChangedListener { view, hourOfDay, minute ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            startTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
            Log.d("time",startTime)
        }
        binding.dialogTimepickEnd.setOnTimeChangedListener { view, hourOfDay, minute ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            endTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
            Log.d("time",endTime)
        }
        binding.timeRangeCheckBox.setOnClickListener {
            if (isChecked) {
                Log.d("isChecked" , "false")
                isChecked = false
                binding.dialogTimepickStart.isEnabled = true
                binding.dialogTimepickEnd.isEnabled =  true
            }else {
                Log.d("isChecked" , "true")
                isChecked = true
                binding.dialogTimepickStart.isEnabled = false
                binding.dialogTimepickEnd.isEnabled =  false
            }
        }
    }
}