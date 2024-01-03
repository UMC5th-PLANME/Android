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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DialogTimePickFragment(context : Context, dialogTimePickInerface: DialogTimePickInerface):Dialog(context){
    private lateinit var binding : FragmentDialogTimepickBinding
    private lateinit var dialogTimePickInerface : DialogTimePickInerface
    private var time: String = ""

    init {
        this.dialogTimePickInerface = dialogTimePickInerface
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogTimepickBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val calendar = Calendar.getInstance()
        time = SimpleDateFormat("a hh:mm", Locale.getDefault()).format(calendar.time)
        clickListener()
    }
    private fun clickListener() {
        binding.dialogTimepickCancel.setOnClickListener {
            dialogTimePickInerface?.onClickCancel() // 여기에 선택된 값을 전달
        }
        binding.dialogTimepickConfirm.setOnClickListener{
            dialogTimePickInerface?.onClickConfirm(time)
        }
        binding.dialogTimepick.setOnTimeChangedListener { view, hourOfDay, minute ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            time = SimpleDateFormat("a hh:mm", Locale.getDefault()).format(calendar.time)
            Log.d("time",time)
        }
    }
}