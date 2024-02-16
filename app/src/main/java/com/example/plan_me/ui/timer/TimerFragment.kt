package com.example.plan_me.ui.timer

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plan_me.R
import com.example.plan_me.data.local.database.SettingDatabase
import com.example.plan_me.data.local.database.TimeDatabase
import com.example.plan_me.data.remote.dto.timer.TimerSettingRes
import com.example.plan_me.data.remote.view.timer.TimerView
import com.example.plan_me.databinding.FragmentTimerFocusBinding
import com.example.plan_me.ui.dialog.DialogCautionResetTimeFragment
import com.example.plan_me.ui.dialog.DialogTimerPickFragment


class TimerFragment : Fragment(), ResetConfirmedListener, TimerView {
    private lateinit var binding: FragmentTimerFocusBinding

    private lateinit var dialogTimerPickFragment: DialogTimerPickFragment
    private lateinit var dialogCautionResetTime: DialogCautionResetTimeFragment

    private var timer: CountDownTimer? = null
    private var remainingTimeInMillis: Long = 0

    var id : Int? = 0
    var category_id: Int? = 0
    var created_at:String? = ""
    var updated_at: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerFocusBinding.inflate(layoutInflater, container, false)
        clickListener()
        return binding.root
    }

    private fun clickListener() {


        // Setting button
        binding.timerFocusSettingBtn.setOnClickListener {
            Log.d("setting: timer-focus", "Time Setting")

            // Timer-Break 에 시간이 남았다면 -> 초기화 알림 문구

            // TimerSetting
            showTimerPickDialog()

        }

        binding.timerFocusTimeTv.setOnClickListener {
            Log.d("setting: timer-focus", "Time Setting")

            // Timer-Break 에 시간이 남았다면 -> 초기화 알림 문구

            // API 연동

            // TimerSetting
            showTimerPickDialog()

        }




        binding.timerFocusPlayBtn.setOnClickListener {

            val settingTimeDB = SettingDatabase.getInstance(this as Context)!!
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
            dialogCautionResetTime = DialogCautionResetTimeFragment(this as Context, this)
            dialogCautionResetTime.show()
        }

    }

    private fun showTimerPickDialog() {
        dialogTimerPickFragment = DialogTimerPickFragment(this, requireContext()) // requireContext()를 통해 현재 컨텍스트를 전달
        dialogTimerPickFragment.clickListener() // 다이얼로그 내에서 클릭 리스너 설정
        dialogTimerPickFragment.show() // 다이얼로그 표시
    }

    // Timer를 시작하거나 이어서 실행하는 함수
    private fun startTimer(millis: Long) {
        timer = object : CountDownTimer(millis, 1000) {     // 1초마다
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished
                updateTimerText()   // Text 를 업데이트
            }

            override fun onFinish() {
                remainingTimeInMillis = 0
                updateTimerText()
                setTimerRunning(false)
                saveElapsedTime()  // Timer 종료 시 저장
            }
        }
        timer?.start()
        setTimerRunning(true)   // 버튼 변경
    }

    // Timer를 일시정지하는 함수
    private fun pauseTimer() {
        timer?.cancel()
        setTimerRunning(false)
    }

    // Timer의 시간을 텍스트뷰에 업데이트하는 함수
    private fun updateTimerText() {
        val seconds = (remainingTimeInMillis / 1000) % 60
        val minutes = (remainingTimeInMillis / (1000 * 60)) % 60
        val hours = remainingTimeInMillis / (1000 * 60 * 60)

        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        binding.timerFocusTimeTv.text = formattedTime
    }

    // 포커스를 잃거나 pause 버튼이 눌린 경우에 진행된 시간을 저장하는 함수
    private fun saveElapsedTime() {
        val settingTimeDB = SettingDatabase.getInstance(this as Context)!!
        val elapsedTimeInMillis = remainingTimeInMillis
        settingTimeDB.SettingTimeDao().updateRemainingFocusTime(elapsedTimeInMillis, 2)

        val saveTime =  settingTimeDB.SettingTimeDao().getTime()
        Log.d("saveElapsedTime", "TimeTable: $saveTime")    // 저장된 시간 확인
    }
    private fun setTimerRunning(isRunning: Boolean){

        if(isRunning) { // Timer 실행 O
            binding.timerFocusPlayBtn.visibility = View.GONE
            binding.timerFocusPauseBtn.visibility = View.VISIBLE
        }
        else {
            binding.timerFocusPlayBtn.visibility = View.VISIBLE
            binding.timerFocusPauseBtn.visibility = View.GONE
        }
    }

    // ResetConfirmedListener 인터페이스 구현
    override fun onResetConfirmed(isConfirmed: Boolean) {
        val timeDB = TimeDatabase.getInstance(this as Context)!!
        val settingTimeDB = SettingDatabase.getInstance(this as Context)!!

        if (isConfirmed) {
            // "저장 및 재설정"이 확인되었을 때의 동작
            // timeTable set:2 시간 -> 0:00:00 으로 바꾸기
            timeDB.timeDao().updateTime(0,0,1,2)

            // SettingTable set:2 시간 ->
            settingTimeDB.SettingTimeDao().updateTime(0,0,0,0,2)

            binding.timerFocusTimeTv.text = "0:00:00"
        }
    }



    fun setFocusTime(focusTime: Int) {

        val seconds = (focusTime / 1000) % 60
        val minutes = (focusTime / (1000 * 60)) % 60
        val hours = focusTime / (1000 * 60 * 60)

        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        Log.d("Dialog -> TimerFragment", "FocusTime : $hours, $minutes, $seconds")
        binding.timerFocusTimeClearTv.text = formattedTime

    }

    fun setBreakTime(breakTime: Int) {
        val seconds = (breakTime / 1000) % 60
        val minutes = (breakTime / (1000 * 60)) % 60
        val hours = breakTime / (1000 * 60 * 60)

        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        Log.d("Dialog -> TimerFragment", "BreakTime : $hours, $minutes, $seconds")
        binding.timerFocusTimeClearTv.text = formattedTime
    }


    private fun showDialog(dialog: Dialog) {
        dialog.show()
    }

    override fun onSetTimerSuccess(response: TimerSettingRes) {
        TODO("Not yet implemented")
    }

    override fun onSetTimerFailure(isSuccess: Boolean, code: String, message: String) {
        TODO("Not yet implemented")
    }

}