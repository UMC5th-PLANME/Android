package com.example.plan_me.ui.timer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.plan_me.data.local.database.SettingDatabase
import com.example.plan_me.data.local.database.TimeDatabase
import com.example.plan_me.data.local.entity.SettingTime
import com.example.plan_me.data.local.entity.Time
import com.example.plan_me.databinding.ActivityTimerSettingBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.Exception

class TimerSettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimerSettingBinding
    private val timerSettingAdapter = TimerVPAdapter(this)

    private val setting = arrayListOf("집중시간", "휴식시간", "반복횟수")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timerSettingAdapter = TimerVPAdapter(this)  // timerSettingAdapter 초기화
        binding.timerTimeSettingVp.adapter = timerSettingAdapter
        binding.timerTimeSettingVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.timerTimeSettingVp.isUserInputEnabled = false

        TabLayoutMediator(binding.timerTimeSettingTl, binding.timerTimeSettingVp) { tab, position ->
            tab.text = setting[position]
        }.attach()

        clickListener()
    }

    private fun clickListener() {
        binding.informationBackBtn.setOnClickListener {
            saveSettingsAndReturn()
        }
    }

    private fun saveSettingsAndReturn() {

        // 각 Fragment 에서 설정된 값을 가져와서 Time 객체에 저장
        val focusTime = timerSettingAdapter.getFocusTime()      // 분
        val breakTime = timerSettingAdapter.getBreakTime()      // 분
        val repeatCount = timerSettingAdapter.getRepeatCount()

        saveToTimeTable(focusTime, breakTime, repeatCount)
        saveToSettingTimeTable(focusTime, focusTime, breakTime, breakTime)


        //  Activity 이동
        if (focusTime == 0 && breakTime != 0) {     // 테이블에 저장된 FocusTime이 0이면 BreakActivity로
            val intent = Intent(this, TimerFocusActivity::class.java)
            startActivity(intent)
        } else{
            // 만약 테이블에 저장된 (FocusTime이 0이 아니면) or (Focus 0, Break 0이면) TimerFocusActivity 로 이동
            val intent = Intent(this, TimerFocusActivity::class.java)
            startActivity(intent)
        }

    }

    private fun saveToTimeTable(focusTime: Int, breakTime: Int, repeatCount: Int) {

        val setNum = 0
        val timeDB = TimeDatabase.getInstance(this)!!
        val existingTime = timeDB.timeDao().getSavedTime(setNum)

        if (existingTime != null) {
            // 이미 setNum = 0 이 테이블에 존재하면 TimeTable 에 업데이트
            timeDB.timeDao().updateTime(focusTime, breakTime, repeatCount, setNum)
        } else {
            // setNum = 0 이 테이블에 없으면 TimeTable 에 업데이트
            timeDB.timeDao().insert(
                Time(
                    focusTime, breakTime, repeatCount
                ).apply {
                    set = setNum
                }
            )
        }

        // Log 찍기
        val loggedTime = timeDB.timeDao().getSavedTime(setNum)
        Log.d("TimeTable", "Insert time : $loggedTime")
    }

    private fun saveToSettingTimeTable(baseFocusTime:Int, remainingFocusTime:Int,baseBreakTime:Int,remainingBreakTime:Int ) {

        val setNum = 0
        val settingTimeDB = SettingDatabase.getInstance(this)!!
        val existingTime = settingTimeDB.SettingTimeDao().getSavedTime(setNum)

        if (existingTime != null) {
            // 이미 setNum = 0 이 테이블에 존재하면 SettingTimeTable 에 업데이트
            settingTimeDB.SettingTimeDao().updateTime(
                (baseFocusTime * 60000).toLong(), (remainingFocusTime * 60000).toLong(),
                (baseBreakTime * 60000).toLong(), (remainingBreakTime * 60000).toLong(), 0 )
        } else {
            // setNum = 0 이 테이블에 없으면 SettingTimeTable 에 업데이트
            settingTimeDB.SettingTimeDao().insert(
                SettingTime(
                    (baseFocusTime * 60000).toLong(), (remainingFocusTime * 60000).toLong(),
                    (baseBreakTime * 60000).toLong(), (remainingBreakTime * 60000).toLong()
                ).apply {
                    set = setNum
                }
            )
        }

        // Log 찍기
        val loggedSettingTime = settingTimeDB.SettingTimeDao().getSavedTime(setNum)
        Log.d("TimeTable", "Insert time : $loggedSettingTime")
    }


}