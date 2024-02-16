package com.example.plan_me.ui.timer

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plan_me.R
import com.example.plan_me.data.local.database.SettingDatabase
import com.example.plan_me.databinding.FragmentTimerFocusBinding
import com.example.plan_me.ui.dialog.DialogCautionResetTimeFragment
import com.example.plan_me.ui.dialog.DialogTimeRangePickFragment
import com.example.plan_me.ui.dialog.DialogTimerPickFragment
import com.example.plan_me.ui.main.MainActivity
import com.example.plan_me.ui.mestory.MestoryActivity
import com.example.plan_me.ui.setting.SettingActivity

class TimerFragment : Fragment() {
    private lateinit var binding: FragmentTimerFocusBinding

    private lateinit var dialogTimerPickFragment: DialogTimerPickFragment
    private lateinit var dialogCautionResetTime: DialogCautionResetTimeFragment


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerFocusBinding.inflate(layoutInflater)
        clickListener()
        return binding.root
    }

    private fun clickListener() {


        // Setting button
        binding.timerFocusSettingBtn.setOnClickListener {
            Log.d("setting: timer-focus", "Time Setting")

            // Timer-Break 에 시간이 남았다면 -> 초기화 알림 문구

            // TimerSetting


        }

        binding.timerFocusTimeTv.setOnClickListener {
            Log.d("setting: timer-focus", "Time Setting")

            // Timer-Break 에 시간이 남았다면 -> 초기화 알림 문구

            // API 연동

            // TimerSetting


        }




        binding.timerFocusPlayBtn.setOnClickListener {

            val settingTimeDB = SettingDatabase.getInstance(this)!!
            var remainingFocusTime = settingTimeDB.SettingTimeDao().getRemainingFocusTime(2)

            remainingTimeInMillis = remainingFocusTime

            if (remainingFocusTime == 0L) return@setOnClickListener

            val time = settingTimeDB.SettingTimeDao().getTime()
            Log.d("TimerFocusActivity", "$time")


            // Timer 생성 or 이어서 실행 -> milliseconds 로 값 전달
            if (remainingTimeInMillis > 0) {
                startTimer(remainingTimeInMillis)
            }

        }

        binding.timerFocusPauseBtn.setOnClickListener {
            pauseTimer()
            saveElapsedTime()
        }

        // Reset 클릭 시
        binding.timerFocusResetBtn.setOnClickListener {
            // Dialog 타이머 초기화 경고 띄우기
            dialogCautionResetTime = DialogCautionResetTimeFragment(this, this)
            dialogCautionResetTime.show()
        }

    }

    private fun showDialog(dialog: Dialog) {
        dialog.show()
    }

}