package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.example.plan_me.databinding.FragmentDialogTimerSettingBinding
import com.example.plan_me.entity.SettingDatabase
import com.example.plan_me.entity.SettingTime
import com.example.plan_me.entity.Time
import com.example.plan_me.entity.TimeDatabase
import com.example.plan_me.ui.timer.TimerSettingListener

class DialogTimerSettingFragment(context : Context): Dialog(context) {
    private lateinit var binding: FragmentDialogTimerSettingBinding

    private val timeDB = TimeDatabase.getInstance(context)!!
    private var focusTime = timeDB.timeDao().getFocusTime(1)
    private var focusTimeToMin = convertMillisecondsToMinutes(focusTime)
    private var breakTime = timeDB.timeDao().getBreakTime(1)
    private var breakTimeToMin = convertMillisecondsToMinutes(breakTime)
    private var repeatCount = timeDB.timeDao().getRepeatCount(1)

    private val settingTimeDB = SettingDatabase.getInstance(context)!!
    private var baseFocusTime = timeDB.timeDao().getFocusTime(2)
    private var remainingFocusTime = settingTimeDB.SettingTimeDao().getBaseFocusTime(1)
    private var baseBreakTime = timeDB.timeDao().getBreakTime(2)
    private var remainingBreakTime = settingTimeDB.SettingTimeDao().getBaseBreakTime(1)

    private var settingListener: TimerSettingListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogTimerSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setTime()
        timeClickListener()

    }

    private fun timeClickListener() {
        // 집중 시간 설정
        binding.dialogTimerFocusTimeUpIv.setOnClickListener {
            focusTimeToMin += 10
            binding.dialogTimerFocusTimeTv.text = focusTimeToMin.toString()
        }
        binding.dialogTimerFocusTimeDownIv.setOnClickListener {
            focusTimeToMin = maxOf(0, focusTimeToMin - 10)
            binding.dialogTimerFocusTimeTv.text = focusTimeToMin.toString()
        }

        // 휴식 시간 설정
        binding.dialogTimerBreakTimeUpIv.setOnClickListener {
            breakTimeToMin += 10
            binding.dialogTimerBreakTimeTv.text = breakTimeToMin.toString()
        }
        binding.dialogTimerBreakTimeDownIv.setOnClickListener {
            breakTimeToMin = maxOf(0, breakTimeToMin - 10)
            binding.dialogTimerBreakTimeTv.text = breakTimeToMin.toString()
        }

        // 반복 횟수 설정
        binding.dialogTimerRepetitionNumUpIv.setOnClickListener {
            repeatCount += 1
            binding.dialogTimerRepetitionNumTv.text = repeatCount.toString()
        }
        binding.dialogTimerRepetitionNumDownIv.setOnClickListener {
            repeatCount = maxOf(1, repeatCount - 1)
            binding.dialogTimerRepetitionNumTv.text = repeatCount.toString()
        }

        // (FOCUS/BREAK 타이머 설정) 취소 버튼 눌렀을 때
        binding.dialogTimeSettingCancel.setOnClickListener{
            dismiss()
        }

        // (FOCUS/BREAK 타이머 설정) 확인 버튼 눌렀을 때 -> 바뀐 값을 set: 2 에 저장
        binding.dialogTimeSettingConfirm.setOnClickListener{

            val changedSetNum = 2
            val existingTime = timeDB.timeDao().getSavedTime(changedSetNum)

            if (existingTime != null) {
                // set: 2가 이미 테이블에 존재하면 TimeTable 에 업데이트
                timeDB.timeDao().updateTime(convertMinutesToMilliseconds(focusTimeToMin),
                                            convertMinutesToMilliseconds(breakTimeToMin),
                                            repeatCount, changedSetNum)

                // SettingTimeTable 에 업데이트
                settingTimeDB.SettingTimeDao().updateTime(convertMinutesToMilliseconds(focusTimeToMin),
                                                    convertMinutesToMilliseconds(focusTimeToMin),
                                                    convertMinutesToMilliseconds(breakTimeToMin),
                                                    convertMinutesToMilliseconds(breakTimeToMin),
                                                    changedSetNum)
            } else {
                // set: 2가 테이블에 없으면 TimeTable 에 삽입
                timeDB.timeDao().insert(
                    Time(
                        convertMinutesToMilliseconds(focusTimeToMin),
                        convertMinutesToMilliseconds(breakTimeToMin),
                        repeatCount
                    ).apply {
                        set = changedSetNum
                    }
                )

                // SettingTimeTable 에 업데이트
                settingTimeDB.SettingTimeDao().insert(
                    SettingTime(
                        convertMinutesToMilliseconds(focusTimeToMin),
                        convertMinutesToMilliseconds(focusTimeToMin),
                        convertMinutesToMilliseconds(breakTimeToMin),
                        convertMinutesToMilliseconds(breakTimeToMin)
                    ).apply {
                        set = changedSetNum
                    }
                )
            }


            // Check Setting
            // set: 1을 다시 50, 10, 1 로 업데이트
            val check_time1 = timeDB.timeDao().getSavedTime(1)
            Log.d("DialogTimerSettingFrag: confirmBtn", "Time: $check_time1")

            //  바뀐 focusTime, breakTime, repeatCount를 set: 2에 출력
            val check_time2 = timeDB.timeDao().getSavedTime(2)
            Log.d("DialogTimerSettingFrag: confirmBtn", "Time: $check_time2")

            val check_time0 = timeDB.timeDao().getTime()
            Log.d("DialogTimerSettingFrag: confirmBtn", "Time: $check_time0")

            val check_setting_time = settingTimeDB.SettingTimeDao().getTime()
            Log.d("DialogTimerSettingFrag: confirmBtn", "Setting Time: $check_setting_time")

            // 변경된 값을 알림
            notifySettingConfirmed(convertMinutesToMilliseconds(focusTimeToMin))

            dismiss()
        }
    }

    private fun setTime(){

        /*// 기본 timer 설정 값을 TimeDao 에 저장 -> set: 1
        timeDB.timeDao().updateTime(
            convertMinutesToMilliseconds(50),
            convertMinutesToMilliseconds(10),
            1,
            1
        )*/

        /*val _time1 = timeDB.timeDao().getSavedTime(1)
        Log.d("Default setting(01)", "Insert time :$_time1")

        focusTime = timeDB.timeDao().getFocusTime(1)*/

        /*settingTimeDB.SettingTimeDao().updateTime(
            convertMinutesToMilliseconds(50),
            convertMinutesToMilliseconds(50),
            convertMinutesToMilliseconds(10),
            convertMinutesToMilliseconds(10),
            1
        )*/

        val _time2 = settingTimeDB.SettingTimeDao().getTime()
        Log.d("DialogTimerSettingFrag", "SettingTime: $_time2")

        // 집중 시간 설정
        binding.dialogTimerFocusTimeTv.text = convertMillisecondsToMinutes(focusTime).toString()
        binding.dialogTimerFocusTimeTv.text = convertMillisecondsToMinutes(focusTime).toString()

        // 휴식 시간 설정
        binding.dialogTimerBreakTimeTv.text = convertMillisecondsToMinutes(breakTime).toString()
        binding.dialogTimerBreakTimeTv.text = convertMillisecondsToMinutes(breakTime).toString()

        // 반복 횟수 설정
        binding.dialogTimerRepetitionNumTv.text = repeatCount.toString()
        binding.dialogTimerRepetitionNumTv.text = repeatCount.toString()

    }

    fun setOnSettingConfirmedListener(listener: TimerSettingListener) {
        this.settingListener = listener
    }

    private fun notifySettingConfirmed(focusTime: Long) {
        settingListener?.onSettingConfirmed(focusTime)
    }

    private fun convertMinutesToMilliseconds(minutes: Long): Long {
        return minutes * 60 * 1000
    }

    private fun convertMillisecondsToMinutes(mils: Long): Long {
        return (mils / (60 * 1000))
    }


}

