package com.example.plan_me.ui.timer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.plan_me.MainActivity
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityTimerFocusBinding
import com.example.plan_me.entity.Time
import com.example.plan_me.entity.TimeDatabase
import com.example.plan_me.ui.dialog.DialogAddFragment
import com.example.plan_me.ui.dialog.DialogTimerSettingFragment
import com.example.plan_me.ui.mestory.MestoryActivity
import com.example.plan_me.ui.setting.SettingActivity


class TimerFocusActivity: AppCompatActivity() {
    private lateinit var binding: ActivityTimerFocusBinding

    private var isFabOpen = false
    private lateinit var dialogAdd: DialogAddFragment
    private lateinit var dialogSetting: DialogTimerSettingFragment
    private lateinit var drawerView: View
    private lateinit var drawerCancel: ImageView

    private var fab_open: Animation? = null
    private var fab_close: Animation? = null

    private var timer: CountDownTimer? = null
    private var remainingTimeInMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerFocusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        drawerView = findViewById(R.id.drawer_layout)
        drawerCancel = findViewById(R.id.drawer_cancel)

        clickListener()
    }

    override fun onBackPressed() {
        if (binding.timerFocusDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.timerFocusDrawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
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
        }
        binding.timerFocusFabPlannerBtn.setOnClickListener {
            Log.d("fab: timer-focus", "timer-break -> mestory")
            switchActivity(MainActivity())
        }
        binding.timerFocusFabSettingBtn.setOnClickListener {
            Log.d("fab: timer-focus", "timer-break -> mestory")
            switchActivity(SettingActivity())
        }
        binding.timerFocusFabAddBtn.setOnClickListener {
            Log.d("fab: timer-focus", "timer-break -> mestory")
            dialogAdd = DialogAddFragment(this)
            dialogAdd.show()
        }
        // Menu button
        binding.timerFocusMenuBtn.setOnClickListener {
            Log.d("menu: timer-focus", "Open menu")
            binding.timerFocusDrawerLayout.openDrawer(drawerView!!)
        }
        drawerCancel.setOnClickListener {
            Log.d("menu: timer-focus", "Close menu")
            binding.timerFocusDrawerLayout.closeDrawers()
        }
        // Setting button
        binding.timerFocusSettingBtn.setOnClickListener {
            Log.d("setting: timer-focus", "Time Setting")

            // Timer-Break 에 시간이 남았다면 -> 초기화 알림 문구


            dialogSetting = DialogTimerSettingFragment(this)

            // Dialog 에서 변경된 FocusTime 값으로 변경
            dialogSetting.setOnSettingConfirmedListener(object : TimerSettingListener {
                override fun onSettingConfirmed(focusTime: Int) {
                    // 초를 시, 분, 초로 변환
                    val hours = focusTime / 60
                    val minutes = focusTime % 60

                    // "hh:mm:ss" 형식으로 포맷팅
                    var formattedTime = String.format("%d:%02d:00", hours, minutes)

                    // UI에 반영
                    binding.timerFocusTimeTv.text = formattedTime
                }
            })
            dialogSetting.show()
        }

        binding.timerFocusPlayBtn.setOnClickListener {
            val timeDB = TimeDatabase.getInstance(this)!!

            // Timer 생성 or 이어서 실행
            var focusTimeInMinutes = timeDB.timeDao().getFocusTime(2)
            if (focusTimeInMinutes > 0){
                startTimer((focusTimeInMinutes * 60 * 1000).toLong())      // 분을 밀리초로 변환
            }

        }
        binding.timerFocusPauseBtn.setOnClickListener {
            pauseTimer()
            saveElapsedTime()
        }
    }

    // Timer를 시작하거나 이어서 실행하는 함수
    private fun startTimer(millisInFuture: Long) {
        timer = object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished
                updateTimerText()
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
        val seconds = (remainingTimeInMillis / 1000).toInt()
        val minutes = seconds / 60
        val hours = minutes / 60
        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60)
        binding.timerFocusTimeTv.text = formattedTime
    }

    // 포커스를 잃거나 pause 버튼이 눌린 경우에 진행된 시간을 저장하는 함수
    private fun saveElapsedTime() {
        val timeDB = TimeDatabase.getInstance(this)!!
        val elapsedTimeInMinutes = (remainingTimeInMillis / (60 * 1000)).toInt()
        timeDB.timeDao().updateFocusTime(elapsedTimeInMinutes, 2)

        val saveTime =  timeDB.timeDao().getTime()
        Log.d("saveElapsedTime", "TimeTable: $saveTime")
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



    private fun switchActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    private fun toggleFab() {
        isFabOpen = if (isFabOpen) {
            binding.timerFocusFabMestoryBtn.startAnimation(fab_close)
            binding.timerFocusFabPlannerBtn.startAnimation(fab_close)
            binding.timerFocusFabSettingBtn.startAnimation(fab_close)
            binding.timerFocusFabAddBtn.startAnimation(fab_close)
            false
        } else {
            binding.timerFocusFabMestoryBtn.startAnimation(fab_open)
            binding.timerFocusFabPlannerBtn.startAnimation(fab_open)
            binding.timerFocusFabSettingBtn.startAnimation(fab_open)
            binding.timerFocusFabAddBtn.startAnimation(fab_open)

            binding.timerFocusFabMestoryBtn.visibility = View.VISIBLE
            binding.timerFocusFabPlannerBtn.visibility = View.VISIBLE
            binding.timerFocusFabSettingBtn.visibility = View.VISIBLE
            binding.timerFocusFabAddBtn.visibility = View.VISIBLE

            binding.timerFocusFabMestoryBtn.setClickable(true)
            binding.timerFocusFabPlannerBtn.setClickable(true)
            binding.timerFocusFabSettingBtn.setClickable(true)
            binding.timerFocusFabAddBtn.setClickable(true)
            true
        }
    }

}