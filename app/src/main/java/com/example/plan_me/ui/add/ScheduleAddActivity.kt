package com.example.plan_me.ui.add

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.plan_me.R
import com.example.plan_me.data.local.entity.Schedule_input
import com.example.plan_me.data.remote.dto.alarm.AlarmDeleteRes
import com.example.plan_me.data.remote.dto.alarm.AlarmGetRes
import com.example.plan_me.data.remote.dto.alarm.AlarmPostRes
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.schedule.AddScheduleRes
import com.example.plan_me.data.remote.dto.schedule.DeleteScheduleRes
import com.example.plan_me.data.remote.dto.schedule.ModifyScheduleRes
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.data.remote.service.alarm.AlarmService
import com.example.plan_me.data.remote.service.schedule.ScheduleService
import com.example.plan_me.data.remote.view.Alarm.AlarmDeleteView
import com.example.plan_me.data.remote.view.Alarm.AlarmGetView
import com.example.plan_me.data.remote.view.Alarm.AlarmPostView
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
import com.example.plan_me.ui.login.InitProfileActivity
import com.example.plan_me.ui.main.MainActivity
import com.example.plan_me.utils.alarm.AlarmFunctions
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
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
    DeleteScheduleView,
    AlarmPostView,
    AlarmGetView,
    AlarmDeleteView{
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
    private  var startTime: String = ""
    private  var endTime: String = ""
    private val customToast = CustomToast

    private lateinit var schedule : ScheduleList
    private lateinit var scheduleInput : Schedule_input

    private var isModify : Boolean = false

    private val ALARM_PERMISSION_REQUEST_CODE = 1001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleAddBinding.inflate(layoutInflater)
        clickListener()
        decide()
        init()
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
            Log.d("schedule",schedule.toString())
            binding.scheduleCategoryDetail.visibility = View.GONE
            binding.scheduleCategoryDelete.visibility = View.VISIBLE

            binding.scheduleNameEt.setText(schedule.title.toString())
            binding.scheduleDateTv.text =
                if(schedule.startDate == schedule.endDate) startDate.monthValue.toString() +"월 " + startDate.dayOfMonth+"일"
                else startDate.monthValue.toString() +"월 " + startDate.dayOfMonth+"일 - "+ endDate.monthValue.toString() +"월 " + endDate.dayOfMonth+"일"
            binding.scheduleTimeTv.text = schedule.start_time.toString() + " ~ " + schedule.end_time.toString()
            if (schedule.alarm) {
                binding.scheduleAlarmTimeTv.text = schedule.alarm_time
                binding.scheduleAlarmSwitch.isChecked = true
                binding.scheduleAlarmTimeView.isEnabled = true
                binding.scheduleAlarmTimeView.setCardBackgroundColor(Color.parseColor("#393939"))
            }

        }else if (intent.hasExtra("category") && intent.hasExtra("categoryList")){
            isModify = false
            binding.scheduleCategoryDetail.visibility = View.VISIBLE
            Log.d("dd", "dd")
            currentCategory = intent.getParcelableExtra("category")!!
            categoryList = intent.getParcelableArrayListExtra("categoryList")!!
            binding.scheduleAlarmTimeView.isEnabled = false
        }
    }

    private fun init(){
        val newColor = ContextCompat.getColor(this, currentCategory.color) // Replace with your desired color resource
        binding.scheduleCategoryEmoticon.text = currentCategory.emoticon
        binding.scheduleCategoryName.text = currentCategory.name
        binding.scheduleTopLayout.background.setColorFilter(newColor, PorterDuff.Mode.SRC_IN)
    }

    private fun clickListener() {
        binding.scheduleAlarmSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                Log.d("dddd", binding.scheduleAlarmSwitch.isChecked.toString())
                binding.scheduleAlarmTimeView.setCardBackgroundColor(Color.parseColor("#393939"))
                binding.scheduleAlarmTimeView.isEnabled = true
            } else {
                binding.scheduleAlarmTimeView.setCardBackgroundColor(Color.parseColor("#A3A3A3"))
                binding.scheduleAlarmTimeView.isEnabled = false
                binding.scheduleAlarmTimeTv.text = "시간 선택"
            }
        }
        binding.scheduleDateBtn.setOnClickListener {
            dialogCalender = DialogCalenderFragment(this, this)
            dialogCalender.show()
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
            var alarm = binding.scheduleAlarmTimeTv.text.toString()
            if (name == "") {
                customToast.createToast(this,"일정 제목을 입력해주세요", 300, false)
            } else if (!::startDate.isInitialized) {
                customToast.createToast(this,"날짜를 설정해주세요", 300, false)
            } else if (startTime == "") {
                customToast.createToast(this,"시간을 설정해주세요", 300, false)
            } else if (alarm == "시간 선택" && binding.scheduleAlarmSwitch.isChecked) {
                customToast.createToast(this,"알람 시간을 설정해주세요", 300, false)
            }else {
                if(alarm == "시간 선택") alarm = "00:00"
                val access_token = "Bearer " + getSharedPreferences(
                    "getRes",
                    MODE_PRIVATE
                ).getString("getAccessToken", "")
                val setScheduleService = ScheduleService()

                if (isModify) {
                    scheduleInput = Schedule_input(
                        schedule.status,
                        schedule.category_id,
                        schedule.repeat_period,
                        name,
                        startTime, //시간 예외
                        endTime,
                        binding.scheduleAlarmSwitch.isChecked,
                        alarm,  //시간 예외 추가해야함
                        startDate.toString(),
                        endDate.toString())

                    setScheduleService.setModifyScheduleView(this)
                    setScheduleService.modifyScheduleFun(access_token, schedule.id, scheduleInput)
                }else {
                    if(alarm == "시간 선택") alarm = "00:00"
                    Log.d("시간 선택", alarm.toString())
                    scheduleInput = Schedule_input(
                        false,
                        currentCategory.categoryId,
                        "NONE",
                        name,
                        startTime,
                        endTime,
                        binding.scheduleAlarmSwitch.isChecked,
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

    private fun postAlarm(scheduleId : Int, date : LocalDate) {
        val access_token = "Bearer " + getSharedPreferences(
            "getRes",
            MODE_PRIVATE
        ).getString("getAccessToken", "")
        val alarmService = AlarmService()
        alarmService.setAlarmPostView(this)
        alarmService.postAlarmList(access_token, scheduleId, date)
    }
    private fun deleteAlarm(scheduleId : Int) {
        val access_token = "Bearer " + getSharedPreferences(
            "getRes",
            MODE_PRIVATE
        ).getString("getAccessToken", "")
        val alarmService = AlarmService()
        alarmService.setAlarmDeleteView(this)
        alarmService.deleteAlarmList(access_token, scheduleId)
    }

    override fun onRangeClickConfirm(startTime: String, endTime: String) {
        Log.d("start", startTime)
        Log.d("end", endTime)
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
        var currentDate = startDate
        if(response.result.alarm) {
            while (currentDate <= endDate) { //api 필요할듯
                postAlarm(response.result.id, currentDate)
                currentDate = currentDate.plusDays(1) // 다음 날짜로 이동
            }
        }
        finish()
    }


    override fun onAddScheduleFailure(response: AddScheduleRes) {
        customToast.createToast(this,"일정이 생성에 실패했습니다", 300, false)
    }

    override fun onModifyScheduleSuccess(response: ModifyScheduleRes) {
        customToast.createToast(this,"일정이 수정되었습니다", 300, true)
        deleteAlarm(schedule.id)
        finish()
    }

    override fun onModifyScheduleFailure(response: ModifyScheduleRes) {
        customToast.createToast(this,"일정 수정에 실패했습니다", 300, false)
    }

    override fun onDeleteScheduleSuccess(response: DeleteScheduleRes) {
        customToast.createToast(this,"일정이 삭제되었습니다", 300, false)
        deleteAlarm(schedule.id)
        finish()
    }

    override fun onDeleteScheduleFailure(response: DeleteScheduleRes) {
        customToast.createToast(this,"일정 삭제에 실패했니다", 300, false)
    }

    override fun onAlarmGetSuccess(response: AlarmGetRes) {

    }

    override fun onAlarmGetFailure(isSuccess: Boolean, code: String, message: String) {
        TODO("Not yet implemented")
    }

    override fun onAlarmPostSuccess(response: AlarmPostRes, date: LocalDate) {
            AlarmFunctions(this).callAlarm(
                date.toString() + " " + scheduleInput.alarm_time + ":00",
                response.result.id,
                currentCategory.emoticon+" "+currentCategory.name+" - "+scheduleInput.title
            )
    }

    override fun onAlarmPostFailure(isSuccess: Boolean, code: String, message: String) {
        TODO("Not yet implemented")
    }

    override fun onAlarmDeleteSuccess(response: AlarmDeleteRes) {
        Log.d("알람 삭제", "성공")
        if (isModify) {
            var currentDate = startDate
            if(schedule.alarm) {
                while (currentDate <= endDate) { //api 필요할듯
                    postAlarm(response.result.id, currentDate)
                    currentDate = currentDate.plusDays(1) // 다음 날짜로 이동
                }
            }
            finish()
        }
    }

    override fun onAlarmDeleteFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("알람 삭제", message)
    }

}