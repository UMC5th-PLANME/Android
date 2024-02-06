package com.example.plan_me.ui.add

import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.plan_me.R
import com.example.plan_me.data.local.entity.Schedule_input
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.schedule.AddScheduleRes
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.service.schedule.ScheduleService
import com.example.plan_me.data.remote.view.schedule.AddScheduleView
import com.example.plan_me.databinding.ActivityScheduleAddBinding
import com.example.plan_me.ui.dialog.CustomToast
import com.example.plan_me.ui.dialog.DialogAlarmFragment
import com.example.plan_me.ui.dialog.DialogCalenderFragment
import com.example.plan_me.ui.dialog.DialogCalenderInterface
import com.example.plan_me.ui.dialog.DialogRepeatFragment
import com.example.plan_me.ui.dialog.DialogRepeatInterface
import com.example.plan_me.ui.dialog.DialogSelectCategoryFragment
import com.example.plan_me.ui.dialog.DialogSelectCategoryInerface
import com.example.plan_me.ui.dialog.DialogTimePickFragment
import com.example.plan_me.ui.dialog.DialogTimePickInerface
import com.example.plan_me.ui.dialog.DialogTimeRangePickFragment
import com.example.plan_me.ui.dialog.DialogTimeRangePickInerface
import java.time.LocalDate

class ScheduleAddActivity():
    AppCompatActivity(),
    DialogTimePickInerface,
    DialogRepeatInterface,
    DialogTimeRangePickInerface,
    DialogCalenderInterface,
    DialogSelectCategoryInerface ,
    AddScheduleView{
    private lateinit var binding : ActivityScheduleAddBinding
    private lateinit var dialogCalender : DialogCalenderFragment
    private lateinit var dialogAlarm : DialogAlarmFragment
    private lateinit var dialogRepeat : DialogRepeatFragment
    private lateinit var dialogTimePick : DialogTimePickFragment
    private lateinit var dialogTimeRangePick : DialogTimeRangePickFragment
    private lateinit var dialogDetail : DialogSelectCategoryFragment
    private lateinit var currentCategory : CategoryList
    private lateinit var categoryList : ArrayList<CategoryList>
    private lateinit var startDate: LocalDate
    private lateinit var endDate: LocalDate
    private lateinit var startTime: String
    private lateinit var endTime: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleAddBinding.inflate(layoutInflater)
        currentCategory = intent.getParcelableExtra("category")!!
        categoryList = intent.getParcelableArrayListExtra("categoryList")!!
        init()
        clickListener()
        setContentView(binding.root)
        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
    }

    private fun init(){
        val newColor = ContextCompat.getColor(this, currentCategory.color) // Replace with your desired color resource
        binding.scheduleCategoryEmoticon.text = currentCategory.emoticon
        binding.scheduleCategoryName.text = currentCategory.name
        binding.scheduleTopLayout.background.setColorFilter(newColor, PorterDuff.Mode.SRC_IN)
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
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        binding.scheduleCategoryDetail.setOnClickListener {
            dialogDetail = DialogSelectCategoryFragment(this, categoryList, this)
            dialogDetail.show()
        }
        binding.scheduleEditCompleteBtn.setOnClickListener {
            val name = binding.scheduleNameEt.text.toString()
            val alarm = binding.scheduleAlarmTimeTv.text.toString()
            val scheduleInput = Schedule_input(true, currentCategory.categoryId, "NONE",name, startTime.substring(3), endTime.substring(3), true,alarm.substring(3), startDate.toString(), endDate.toString())

            Log.d("schedule", scheduleInput.toString())
            val access_token = "Bearer " + getSharedPreferences("getRes", MODE_PRIVATE).getString("getAccessToken", "")
            val setScheduleService = ScheduleService()
            setScheduleService.setAddScheduleView(this)
            setScheduleService.addScheduleFun(access_token!!, scheduleInput)
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
            this.startTime = startTime
            this.endTime = endTime
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
            this.startDate = start!!
        }
        else {
            binding.scheduleDateTv.text = start!!.monthValue.toString() +"월 " + start!!.dayOfMonth+"일 - "+ end!!.monthValue.toString() +"월 " + end!!.dayOfMonth+"일"
            this.startDate = start!!
            this.endDate = start!!
        }
        dialogCalender.dismiss()
    }

    override fun onClickCategory(position: Int) {
        currentCategory = categoryList[position]
        init()
        dialogDetail.dismiss()
    }

    override fun onAddScheduleSuccess(response: AddScheduleRes) {
        val customToast = CustomToast
        customToast.createToast(this,"일정이 생성되었습니다.", 300)
        finish()
    }

    override fun onAddScheduleFailure(response: AddScheduleRes) {
        TODO("Not yet implemented")
    }

}