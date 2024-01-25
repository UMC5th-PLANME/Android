package com.example.plan_me.ui.timer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.plan_me.MainActivity
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityTimerBreakBinding
import com.example.plan_me.ui.dialog.DialogAddFragment
import com.example.plan_me.ui.dialog.DialogTimerSettingFragment
import com.example.plan_me.ui.mestory.MestoryActivity

class TimerBreakActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimerBreakBinding

    private var isFabOpen = false
    private lateinit var dialogAdd : DialogAddFragment
    private lateinit var dialogSetting: DialogTimerSettingFragment
    private lateinit var drawerView: View
    private lateinit var drawerCancel: ImageView

    private var fab_open: Animation? = null
    private var fab_close: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBreakBinding.inflate(layoutInflater)

        setContentView(binding.root)
        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)
        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        drawerView = findViewById(R.id.drawer_layout)

        clickListener()
    }

    override fun onBackPressed() {
        if (binding.timerBreakDrawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.timerBreakDrawerLayout.closeDrawers()
        }
        else {
            super.onBackPressed()
        }
    }

    private fun clickListener() {
        binding.timerBreakFabMenuBtn.setOnClickListener {
            Log.d("fab: timer-break","timer-break")
            toggleFab()
        }
        binding.timerBreakFabMestoryBtn.setOnClickListener {
            Log.d("fab: timer-break", "timer-break -> mestory")
            switchActivity(MestoryActivity())
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        binding.timerBreakFabPlannerBtn.setOnClickListener {
            Log.d("fab: timer-break", "timer-break -> mestory")
            switchActivity(MainActivity())
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }
        binding.timerBreakFabSettingBtn.setOnClickListener {
            Log.d("fab: timer-break", "timer-break -> mestory")
        }
        binding.timerBreakMenuBtn.setOnClickListener{
            Log.d("menu: timer-break", "Open menu")
            binding.timerBreakDrawerLayout.openDrawer(drawerView!!)
        }
        drawerCancel.setOnClickListener {
            Log.d("menu: timer-break", "Close menu")
            binding.timerBreakDrawerLayout.closeDrawers()
        }
        binding.timerBreakSettingBtn.setOnClickListener {
            Log.d("setting: timer-focus", "Time Setting")

            // Timer-Break 에 시간이 남았다면 -> 초기화 알림 문구

            dialogSetting = DialogTimerSettingFragment(this)
            dialogSetting.show()
        }
    }

    private fun switchActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    private fun toggleFab() {
        isFabOpen = if (isFabOpen) {
            binding.timerBreakFabMestoryBtn.startAnimation(fab_close)
            binding.timerBreakFabPlannerBtn.startAnimation(fab_close)
            binding.timerBreakFabSettingBtn.startAnimation(fab_close)
            false
        } else {
            binding.timerBreakFabMestoryBtn.startAnimation(fab_open)
            binding.timerBreakFabPlannerBtn.startAnimation(fab_open)
            binding.timerBreakFabSettingBtn.startAnimation(fab_open)

            binding.timerBreakFabMestoryBtn.visibility = View.VISIBLE
            binding.timerBreakFabPlannerBtn.visibility = View.VISIBLE
            binding.timerBreakFabSettingBtn.visibility = View.VISIBLE

            binding.timerBreakFabMestoryBtn.setClickable(true)
            binding.timerBreakFabPlannerBtn.setClickable(true)
            binding.timerBreakFabSettingBtn.setClickable(true)
            true
        }
    }
}