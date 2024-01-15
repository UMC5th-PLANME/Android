package com.example.plan_me.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.example.plan_me.databinding.FragmentDialogTimerSettingBinding
import com.example.plan_me.entity.Time
import com.example.plan_me.entity.TimeDatabase
import com.example.plan_me.ui.timer.TimerFocusActivity

class DialogTimerSettingFragment(context : Context): Dialog(context) {
    private lateinit var binding: FragmentDialogTimerSettingBinding

    private val timeDB = TimeDatabase.getInstance(context)!!
    private val time = timeDB.timeDao().getTime()
    private var focusTime = timeDB.timeDao().getFocusTime(1)
    private var breakTime = timeDB.timeDao().getBreakTime(1)
    private var repeatCount = timeDB.timeDao().getRepeatCount(1)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogTimerSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        timeClickListener()

    }

    private fun timeClickListener() {
        // 집중 시간 설정
        binding.dialogTimerFocusTimeUpIv.setOnClickListener {
            Log.d("focusTime", focusTime.toString())
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

        // (FOCUS/BREAK 타이머 설정) 확인 버튼 눌렀을 때
        binding.dialogTimeSettingConfirm.setOnClickListener{
            timeDB.timeDao().updateFocusTime(focusTime, 1)
            timeDB.timeDao().updateBreakTime(breakTime, 1)
            timeDB.timeDao().updateRepeatCount(repeatCount, 1)

            // Check Setting
            val _time = timeDB.timeDao().updateTime(focusTime, breakTime, repeatCount, 1)
            Log.d("Update time", "$_time")
            dismiss()
        }
    }

}

