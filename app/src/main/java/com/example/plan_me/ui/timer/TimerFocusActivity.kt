package com.example.plan_me.ui.timer

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.plan_me.MainActivity
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityTimerFocusBinding
import com.example.plan_me.databinding.FragmentDialogCautionResetTimeBinding
import com.example.plan_me.entity.SettingDatabase
import com.example.plan_me.entity.SettingTime
import com.example.plan_me.entity.SettingTimeDatabase
import com.example.plan_me.entity.Time
import com.example.plan_me.entity.TimeDatabase
import com.example.plan_me.ui.add.ScheduleAddActivity
import com.example.plan_me.ui.dialog.DialogAddFragment
import com.example.plan_me.ui.dialog.DialogCautionResetTimeFragment
import com.example.plan_me.ui.dialog.DialogTimerSettingFragment
import com.example.plan_me.ui.mestory.MestoryActivity
import com.example.plan_me.ui.setting.SettingActivity


class TimerFocusActivity: AppCompatActivity(), ResetConfirmedListener {
    private lateinit var binding: ActivityTimerFocusBinding

    private var isFabOpen = false

    private lateinit var dialogSetting: DialogTimerSettingFragment
    private lateinit var dialogCautionResetTime: DialogCautionResetTimeFragment

    private lateinit var drawerView: View
    private lateinit var drawerAdd: TextView

    private var fab_open: Animation? = null
    private var fab_close: Animation? = null

    private var timer: CountDownTimer? = null
    private var remainingTimeInMillis: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerFocusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        drawerView = findViewById(R.id.drawer_layout)
        drawerAdd = findViewById(R.id.drawer_add_tv)

        updateTimerText()
        setTime()
        clickListener()


    }

    override fun onBackPressed() {
        if (binding.timerFocusDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.timerFocusDrawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
    }

    private fun clickListener() {
        // Floating Action Button
        binding.timerFocusFabMenuBtn.setOnClickListener {
            Log.d("fab: timer-focus", "timer-break")
            toggleFab()
        }
        binding.timerFocusFabMestoryBtn.setOnClickListener {
            Log.d("fab: timer-focus", "timer-break -> mestory")
            switchActivity(MestoryActivity())
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        binding.timerFocusFabPlannerBtn.setOnClickListener {
            Log.d("fab: timer-focus", "timer-break -> mestory")
            switchActivity(MainActivity())
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        binding.timerFocusFabSettingBtn.setOnClickListener {
            Log.d("fab: timer-focus", "timer-break -> mestory")
            switchActivity(SettingActivity())
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        // Menu button
        binding.timerFocusMenuBtn.setOnClickListener {
            Log.d("menu: timer-focus", "Open menu")
            binding.timerFocusDrawerLayout.openDrawer(drawerView!!)
        }
        drawerAdd.setOnClickListener {
            showDialog(DialogAddFragment(this@TimerFocusActivity))
        }
        binding.timerFocusSettingBtn.setOnClickListener {
            Log.d("setting: timer-focus", "Time Setting")

            // Timer-Break 에 시간이 남았다면 -> 초기화 알림 문구


            dialogSetting = DialogTimerSettingFragment(this)

            // Dialog 에서 변경된 FocusTime 값으로 변경
            dialogSetting.setOnSettingConfirmedListener(object : TimerSettingListener {
                override fun onSettingConfirmed(focusTime: Long) {
                    Log.d("Dialog -> FocusActivity", "onSettingConfirmed: $focusTime")

                    val seconds = (focusTime / 1000) % 60
                    val minutes = (focusTime / (1000 * 60)) % 60
                    val hours = focusTime / (1000 * 60 * 60)

                    val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                    Log.d("Dialog -> FocusActivity", "$hours, $minutes, $seconds")
                    binding.timerFocusTimeTv.text = formattedTime
                }
            })
            dialogSetting.show()
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

        binding.timerFocusTimeTv.setOnClickListener {
            Log.d("setting: timer-focus", "Time Setting")

            // Timer-Break 에 시간이 남았다면 -> 초기화 알림 문구


            dialogSetting = DialogTimerSettingFragment(this)

            // Dialog 에서 변경된 FocusTime 값으로 변경
            dialogSetting.setOnSettingConfirmedListener(object : TimerSettingListener {
                override fun onSettingConfirmed(focusTime: Long) {
                    Log.d("Dialog -> FocusActivity", "onSettingConfirmed: $focusTime")

                    val seconds = (focusTime / 1000) % 60
                    val minutes = (focusTime / (1000 * 60)) % 60
                    val hours = focusTime / (1000 * 60 * 60)

                    val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                    Log.d("Dialog -> FocusActivity", "$hours, $minutes, $seconds")
                    binding.timerFocusTimeTv.text = formattedTime
                }
            })
            dialogSetting.show()
        }

        // Reset 클릭 시
        binding.timerFocusResetBtn.setOnClickListener {
            // Dialog 타이머 초기화 경고 띄우기
            dialogCautionResetTime = DialogCautionResetTimeFragment(this, this)
            dialogCautionResetTime.show()
        }

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
        val settingTimeDB = SettingDatabase.getInstance(this)!!
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

    private fun setTime(){

        val timeDB = TimeDatabase.getInstance(this)!!

        if (timeDB.timeDao().getTime() != null) return

        timeDB.timeDao().insert(
            Time(
                convertMinutesToMilliseconds(50),
                convertMinutesToMilliseconds(10),
                1
            ).apply {
                set = 1
            }
        )

        val settingTimeDB = SettingDatabase.getInstance(this)!!

        settingTimeDB.SettingTimeDao().insert(
            SettingTime(
                convertMinutesToMilliseconds(50),
                convertMinutesToMilliseconds(50),
                convertMinutesToMilliseconds(10),
                convertMinutesToMilliseconds(10),
            ).apply {
                set = 1
            }
        )
    }

    // ResetConfirmedListener 인터페이스 구현
    override fun onResetConfirmed(isConfirmed: Boolean) {
        val timeDB = TimeDatabase.getInstance(this)!!
        val settingTimeDB = SettingDatabase.getInstance(this)!!

        if (isConfirmed) {
            // "저장 및 재설정"이 확인되었을 때의 동작
            // timeTable set:2 시간 -> 0:00:00 으로 바꾸기
            timeDB.timeDao().updateTime(0,0,1,2)

            // SettingTable set:2 시간 ->
            settingTimeDB.SettingTimeDao().updateTime(0,0,0,0,2)

            binding.timerFocusTimeTv.text = "0:00:00"
        }
    }

    private fun convertMinutesToMilliseconds(minutes: Long): Long {
        return minutes * 60 * 1000
    }

    private fun showDialog(dialog: Dialog) {
        dialog.show()
    }

    private fun switchActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    private fun toggleFab() {
        isFabOpen = if (isFabOpen) {
            binding.timerFocusFabMestoryBtn.startAnimation(fab_close)
            binding.timerFocusFabPlannerBtn.startAnimation(fab_close)
            binding.timerFocusFabSettingBtn.startAnimation(fab_close)
            false
        } else {
            binding.timerFocusFabMestoryBtn.startAnimation(fab_open)
            binding.timerFocusFabPlannerBtn.startAnimation(fab_open)
            binding.timerFocusFabSettingBtn.startAnimation(fab_open)

            binding.timerFocusFabMestoryBtn.visibility = View.VISIBLE
            binding.timerFocusFabPlannerBtn.visibility = View.VISIBLE
            binding.timerFocusFabSettingBtn.visibility = View.VISIBLE

            binding.timerFocusFabMestoryBtn.setClickable(true)
            binding.timerFocusFabPlannerBtn.setClickable(true)
            binding.timerFocusFabSettingBtn.setClickable(true)
            true
        }
    }


}

