package com.example.plan_me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.plan_me.databinding.FragmentScheduleAddBinding
import com.example.plan_me.ui.dialog.DialogAlarmFragment
import com.example.plan_me.ui.dialog.DialogRepeatFragment
import com.example.plan_me.ui.dialog.DialogRepeatInterface
import com.example.plan_me.ui.dialog.DialogTimePickFragment
import com.example.plan_me.ui.dialog.DialogTimePickInerface
import com.example.plan_me.ui.dialog.DialogTimeRangePickFragment
import com.example.plan_me.ui.dialog.DialogTimeRangePickInerface

class ScheduleAddFragment:Fragment(), DialogTimePickInerface, DialogRepeatInterface,
    DialogTimeRangePickInerface {
    private lateinit var binding : FragmentScheduleAddBinding
    private lateinit var dialogAlarm : DialogAlarmFragment
    private lateinit var dialogRepeat : DialogRepeatFragment
    private lateinit var dialogTimePick : DialogTimePickFragment
    private lateinit var dialogTimeRangePick : DialogTimeRangePickFragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentScheduleAddBinding.inflate(layoutInflater)
        clickListener()
        return binding.root
    }

    private fun clickListener() {
        binding.scheduleAlarmTv.setOnClickListener {
            dialogAlarm = DialogAlarmFragment(requireContext())
            dialogAlarm.show()
        }
        binding.scheduleRepeatTv.setOnClickListener {
            dialogRepeat = DialogRepeatFragment(requireContext(), this)
            dialogRepeat.show()
        }
        binding.scheduleAlarmTimeView.setOnClickListener {
            dialogTimePick = DialogTimePickFragment(requireContext(), this)
            dialogTimePick.show()
        }
        binding.scheduleTimeBtn.setOnClickListener{
            dialogTimeRangePick = DialogTimeRangePickFragment(requireContext(), this)
            dialogTimeRangePick.show()
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
        if (isVaildRange(startTime,endTime)) {
            binding.scheduleTimeTv.text = startTime + " ~ " + endTime
            dialogTimeRangePick.dismiss()
        }
        else {
            Toast.makeText(requireContext(), "inVaild range", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRangeClickCancel() {
        dialogTimeRangePick.dismiss()
    }

}