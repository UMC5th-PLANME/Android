package com.example.plan_me.ui.add

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.plan_me.R
import com.example.plan_me.data.local.entity.Schedule_input
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.schedule.AddScheduleRes
import com.example.plan_me.data.remote.dto.schedule.DeleteScheduleRes
import com.example.plan_me.data.remote.dto.schedule.ModifyScheduleRes
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.service.schedule.ScheduleService
import com.example.plan_me.data.remote.view.schedule.AddScheduleView
import com.example.plan_me.data.remote.view.schedule.DeleteScheduleView
import com.example.plan_me.data.remote.view.schedule.ModifyScheduleView
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
import com.example.plan_me.ui.main.MainActivity
import org.jetbrains.annotations.Async.Schedule
import java.time.LocalDate

class ScheduleAddActivity():
    AppCompatActivity(),
    DialogTimePickInerface,
    DialogRepeatInterface,
    DialogTimeRangePickInerface,
    DialogCalenderInterface,
    DialogSelectCategoryInerface ,
    AddScheduleView,
    ModifyScheduleView,
    DeleteScheduleView{
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
    private val customToast = CustomToast

    private lateinit var schedule : ScheduleList

    private var isModify : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleAddBinding.inflate(layoutInflater)
        decide()
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

    private fun decide() {
        if (intent.hasExtra("schedule") && intent.hasExtra("schedule_category")) {
            isModify = true
            schedule = intent.getParcelableExtra("schedule")!!
            currentCategory = intent.getParcelableExtra("schedule_category")!!
            startTime = schedule.start_time
            endTime = schedule.end_time
            startDate = LocalDate.parse(schedule.startDate)
            endDate = LocalDate.parse(schedule.endDate)
            Log.d("schedule","schedule")
            binding.scheduleCategoryDetail.visibility = View.GONE
            binding.scheduleCategoryDelete.visibility = View.VISIBLE

            binding.scheduleNameEt.setText(schedule.title.toString())
            binding.scheduleDateTv.text =
                if(schedule.startDate == schedule.endDate) startDate.monthValue.toString() +"월 " + startDate.dayOfMonth+"일"
                else startDate.monthValue.toString() +"월 " + startDate.dayOfMonth+"일 - "+ endDate.monthValue.toString() +"월 " + endDate.dayOfMonth+"일"
            binding.scheduleTimeTv.text = schedule.start_time.toString() + " ~ " + schedule.end_time.toString()
            binding.scheduleRepeatTv.text = schedule.repeat_period
            binding.scheduleAlarmTv.text = schedule.alarm.toString()
            binding.scheduleAlarmTimeTv.text = schedule.alarm_time

        }else if (intent.hasExtra("category") && intent.hasExtra("categoryList")){
            isModify = false
            Log.d("dd", "dd")
            currentCategory = intent.getParcelableExtra("category")!!
            categoryList = intent.getParcelableArrayListExtra("categoryList")!!
        }
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
        binding.scheduleCategoryDelete.setOnClickListener {
            val access_token = "Bearer " + getSharedPreferences(
                "getRes",
                MODE_PRIVATE
            ).getString("getAccessToken", "")
            val setScheduleService = ScheduleService()
            setScheduleService.setDeleteScheduleView(this)
            setScheduleService.deleteScheduleFun(access_token, schedule.id)
        }
        binding.scheduleEditCompleteBtn.setOnClickListener {

            val name = binding.scheduleNameEt.text.toString()
            val alarm = binding.scheduleAlarmTimeTv.text.toString()
            if (name == "") {
                customToast.createToast(this,"일정 제목을 입력해주세요", 300, false)
            } else if (!::startDate.isInitialized) {
                customToast.createToast(this,"날짜를 설정해주세요", 300, false)
            } else if (startTime.isEmpty()) {
                customToast.createToast(this,"시간을 설정해주세요", 300, false)
            } else if (alarm == "") {
                customToast.createToast(this,"알람 시간을 설정해주세요", 300, false)
            }else {
                val access_token = "Bearer " + getSharedPreferences(
                    "getRes",
                    MODE_PRIVATE
                ).getString("getAccessToken", "")
                val setScheduleService = ScheduleService()

                if (isModify) {
                    val schedule_input = Schedule_input(
                        schedule.status,
                        schedule.category_id,
                        schedule.repeat_period,
                        name,
                        startTime, //시간 예외
                        endTime,
                        schedule.alarm,
                        alarm,  //시간 예외 추가해야함
                        startDate.toString(),
                        endDate.toString())

                    setScheduleService.setModifyScheduleView(this)
                    setScheduleService.modifyScheduleFun(access_token, schedule.id, schedule_input)
                }else {
                    val scheduleInput = Schedule_input(
                        false,
                        currentCategory.categoryId,
                        "NONE",
                        name,
                        startTime,
                        endTime,
                        true,
                        alarm,
                        startDate.toString(),
                        endDate.toString()
                    )
                    setScheduleService.setAddScheduleView(this)
                    setScheduleService.addScheduleFun(access_token!!, scheduleInput)
                }
            }
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
        val startHour = startTime.substring(0,2)
        val endHour = endTime.substring(0,2)
        val startMinute = startTime.substring(3,5)
        val endMinute = endTime.substring(3,5)
        if (startHour.toInt() > endHour.toInt()) {
            return false
        }
        else if (startHour.toInt()==endHour.toInt())  {
            if (startMinute.toInt() < endMinute.toInt()) return true
            else return false
        }
        else if (startHour.toInt() < endHour.toInt()) return true
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
            val customToast = CustomToast
            customToast.createToast(this,"올바른 시간을 설정해주세요", 500, false)
        }
    }

    override fun onRangeClickCancel() {
        dialogTimeRangePick.dismiss()
    }

    override fun onClickCalenderConfirm(start : LocalDate?, end: LocalDate?) {
        if (end == start) {
            binding.scheduleDateTv.text = start!!.monthValue.toString() +"월 " + start!!.dayOfMonth+"일"
            this.startDate = start!!
            this.endDate = end!!
        }
        else {
            binding.scheduleDateTv.text = start!!.monthValue.toString() +"월 " + start!!.dayOfMonth+"일 - "+ end!!.monthValue.toString() +"월 " + end!!.dayOfMonth+"일"
            this.startDate = start!!
            this.endDate = end!!
        }
        dialogCalender.dismiss()
    }

    override fun onClickCategory(position: Int) {
        currentCategory = categoryList[position]
        init()
        dialogDetail.dismiss()
    }

    override fun onAddScheduleSuccess(response: AddScheduleRes) {
        customToast.createToast(this,"일정이 생성되었습니다", 300, true)
        finish()
    }


    override fun onAddScheduleFailure(response: AddScheduleRes) {
        customToast.createToast(this,"일정이 생성에 실패했습니다", 300, false)
    }

    override fun onModifyScheduleSuccess(response: ModifyScheduleRes) {
        customToast.createToast(this,"일정이 수정되었습니다", 300, true)
        finish()
    }

    override fun onModifyScheduleFailure(response: ModifyScheduleRes) {
        customToast.createToast(this,"일정 수정에 실패했습니다", 300, false)
    }

    override fun onDeleteScheduleSuccess(response: DeleteScheduleRes) {
        customToast.createToast(this,"일정이 삭제되었습니다", 300, false)
        finish()
    }

    override fun onDeleteScheduleFailure(response: DeleteScheduleRes) {
        customToast.createToast(this,"일정 삭제에 실패했니다", 300, false)
    }


}