package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.example.plan_me.databinding.FragmentDialogTimerSettingBinding
import com.example.plan_me.entity.Time
import com.example.plan_me.entity.TimeDatabase
import com.example.plan_me.ui.timer.TimerSettingListener

class DialogTimerSettingFragment(context : Context): Dialog(context) {
    private lateinit var binding: FragmentDialogTimerSettingBinding

    private val timeDB = TimeDatabase.getInstance(context)!!
    private val time = timeDB.timeDao().getTime()
    private var focusTime = timeDB.timeDao().getFocusTime(1)
    private var breakTime = timeDB.timeDao().getBreakTime(1)
    private var repeatCount = timeDB.timeDao().getRepeatCount(1)

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
            focusTime += 10
            binding.dialogTimerFocusTimeTv.text = focusTime.toString()
        }
        binding.dialogTimerFocusTimeDownIv.setOnClickListener {
            focusTime = maxOf(0, focusTime - 10)
            binding.dialogTimerFocusTimeTv.text = focusTime.toString()
        }

        // 휴식 시간 설정
        binding.dialogTimerBreakTimeUpIv.setOnClickListener {
            breakTime += 10
            binding.dialogTimerBreakTimeTv.text = breakTime.toString()
        }
        binding.dialogTimerBreakTimeDownIv.setOnClickListener {
            breakTime = maxOf(0, breakTime - 10)
            binding.dialogTimerBreakTimeTv.text = breakTime.toString()
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

            val focusTimeMillis = convertMinutesToMilliseconds(focusTime)
            val breakTimeMillis = convertMinutesToMilliseconds(breakTime)

            if (existingTime != null) {
                // set: 2가 이미 테이블에 존재하면 업데이트
                timeDB.timeDao().updateTime(focusTime, breakTime, repeatCount, changedSetNum)
            } else {
                // set: 2가 테이블에 없으면 삽입
                timeDB.timeDao().insert(
                    Time(
                        focusTimeMillis,
                        breakTimeMillis,
                        repeatCount
                    ).apply {
                        set = changedSetNum
                    }
                )
            }

            // Check Setting
            // set: 1을 다시 50, 10, 1 로 업데이트
            val check_time1 = timeDB.timeDao().getSavedTime(1)
            Log.d("Default setting(1)", "Insert time :$check_time1")

            //  바뀐 focusTime, breakTime, repeatCount를 set: 2에 출력
            val check_time2 = timeDB.timeDao().getSavedTime(2)
            Log.d("Default setting(2)", "Insert time :$check_time2")

            val check_time0 = timeDB.timeDao().getTime()
            Log.d("Default setting(0)", "$check_time0")

            // 변경된 값을 알림
            notifySettingConfirmed(focusTimeMillis)


            dismiss()
        }
    }

    private fun setTime(){

        val defaultSetNum= 1

        if (time.isNotEmpty()) return

        // 기본 timer 설정 값을 Dao 에 저장 -> set: 1
        timeDB.timeDao().insert(
            Time(
                convertMinutesToMilliseconds(50),
                convertMinutesToMilliseconds(10),
                1
            ).apply {
                set = defaultSetNum
            }
        )

        val _time1 = timeDB.timeDao().getSavedTime(1)
        Log.d("Default setting(01)", "Insert time :$_time1")

        focusTime = timeDB.timeDao().getFocusTime(1)

        // 집중 시간 설정
        binding.dialogTimerFocusTimeTv.text = focusTime.toString()
        binding.dialogTimerFocusTimeTv.text = focusTime.toString()

        // 휴식 시간 설정
        binding.dialogTimerBreakTimeTv.text = breakTime.toString()
        binding.dialogTimerBreakTimeTv.text = breakTime.toString()

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

}
