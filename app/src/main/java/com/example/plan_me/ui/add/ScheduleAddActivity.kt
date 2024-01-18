package com.example.plan_me.ui.add

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityScheduleAddBinding
import com.example.plan_me.ui.dialog.DialogAlarmFragment
import com.example.plan_me.ui.dialog.DialogCalenderFragment
import com.example.plan_me.ui.dialog.DialogCalenderInterface
import com.example.plan_me.ui.dialog.DialogRepeatFragment
import com.example.plan_me.ui.dialog.DialogRepeatInterface
import com.example.plan_me.ui.dialog.DialogTimePickFragment
import com.example.plan_me.ui.dialog.DialogTimePickInerface
import com.example.plan_me.ui.dialog.DialogTimeRangePickFragment
import com.example.plan_me.ui.dialog.DialogTimeRangePickInerface
import java.time.LocalDate

class ScheduleAddActivity: AppCompatActivity(), DialogTimePickInerface, DialogRepeatInterface,
    DialogTimeRangePickInerface, DialogCalenderInterface {
    private lateinit var binding : ActivityScheduleAddBinding
    private lateinit var dialogCalender : DialogCalenderFragment
    private lateinit var dialogAlarm : DialogAlarmFragment
    private lateinit var dialogRepeat : DialogRepeatFragment
    private lateinit var dialogTimePick : DialogTimePickFragment
    private lateinit var dialogTimeRangePick : DialogTimeRangePickFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleAddBinding.inflate(layoutInflater)
        clickListener()
        setContentView(binding.root)
    }

    private fun clickListener() {
        binding.scheduleAlarmTv.setOnClickListener {
            dialogAlarm = DialogAlarmFragment(this)
            dialogAlarm.show()
        }
        binding.scheduleDateBtn.setOnClickListener {
            dialogCalender = DialogCalenderFragment(this, this)
            dialogCalender.show()
        }
        binding.scheduleRepeatTv.setOnClickListener {
            dialogRepeat = DialogRepeatFragment(this, this)
            dialogRepeat.show()
        }
        binding.scheduleAlarmTimeView.setOnClickListener {
            dialogTimePick = DialogTimePickFragment(this, this)
            dialogTimePick.show()
        }
        binding.scheduleTimeBtn.setOnClickListener{
            dialogTimeRangePick = DialogTimeRangePickFragment(this, this)
            dialogTimeRangePick.show()
        }
        binding.scheduleBackBtn.setOnClickListener {
            finish()
        }
    }

    override fun onClickConfirm(time:String) {
        binding.scheduleAlarmTimeTv.text = time
        dialogTimePick.dismiss()
    }

    override fun onClickCancel() {
        dialogTimePick.dismiss()
    }

    override fun onClickRepeatItem() {
        TODO("Not yet implemented")
    }

    override fun onClickRepeatCancel() {
        dialogRepeat.dismiss()
    }
    private fun isVaildRange(startTime: String, endTime: String) :Boolean{  //조건 더 있어야할듯함
        if (startTime.substring(0,2) == endTime.substring(0,2)) {
            val st = startTime.substring(3 , 5)+startTime.substring(6,8)
            val et = endTime.substring(3 , 5)+endTime.substring(6,8)
            if (st.toInt() < et.toInt()) return true
            else return false
        }
        else if (startTime.substring(0 ,2) == "AM" && endTime.substring(0 ,2) == "PM") return true
        else if (startTime.substring(0 ,2) == "PM" && endTime.substring(0 , 2) == "AM") return false
        else return false
    }
    override fun onRangeClickConfirm(startTime: String, endTime: String) {
        Log.d("start", startTime.toString())
        Log.d("end", endTime.toString())
        if (isVaildRange(startTime,endTime)) {
            binding.scheduleTimeTv.text = startTime + " ~ " + endTime
            dialogTimeRangePick.dismiss()
        }
        else {
            Toast.makeText(this, "inVaild range", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRangeClickCancel() {
        dialogTimeRangePick.dismiss()
    }

    override fun onClickCalenderConfirm(start : LocalDate?, end: LocalDate?) {
        if (end == null) {
            binding.scheduleDateTv.text = start!!.monthValue.toString() +"월 " + start!!.dayOfMonth+"일"
        }
        else {
            binding.scheduleDateTv.text = start!!.monthValue.toString() +"월 " + start!!.dayOfMonth+"일 - "+ end!!.monthValue.toString() +"월 " + end!!.dayOfMonth+"일"
        }
        dialogCalender.dismiss()
    }

}