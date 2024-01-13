package com.example.plan_me.ui.setting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.MainActivity
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityInformationBinding
import com.example.plan_me.ui.timer.TimerFocusActivity

class InformationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityInformationBinding
    private var isFabOpen = false
    private var fab_open: Animation? = null
    private var fab_close: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        clickListener()
    }

    private fun clickListener() {
        binding.settingFabSettingBtn.setOnClickListener {
            Log.d("fab","fab")
            toggleFab()
        }
        binding.settingFabMestoryBtn.setOnClickListener {
        }
        binding.settingFabTimerBtn.setOnClickListener {
            switchActivity(TimerFocusActivity())
        }
        binding.settingFabPlannerBtn.setOnClickListener {
            switchActivity(MainActivity())
        }
        binding.settingFabAddBtn.setOnClickListener {
        }
    }
    private fun switchActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    private fun toggleFab() {
        isFabOpen = if (isFabOpen) {
            binding.settingFabMestoryBtn.startAnimation(fab_close)
            binding.settingFabTimerBtn.startAnimation(fab_close)
            binding.settingFabPlannerBtn.startAnimation(fab_close)
            binding.settingFabAddBtn.startAnimation(fab_close)
            false
        } else {
            binding.settingFabMestoryBtn.startAnimation(fab_open)
            binding.settingFabTimerBtn.startAnimation(fab_open)
            binding.settingFabPlannerBtn.startAnimation(fab_open)
            binding.settingFabAddBtn.startAnimation(fab_open)

            binding.settingFabMestoryBtn.visibility = View.VISIBLE
            binding.settingFabTimerBtn.visibility = View.VISIBLE
            binding.settingFabPlannerBtn.visibility = View.VISIBLE
            binding.settingFabAddBtn.visibility = View.VISIBLE

            binding.settingFabMestoryBtn.setClickable(true)
            binding.settingFabTimerBtn.setClickable(true)
            binding.settingFabPlannerBtn.setClickable(true)
            binding.settingFabAddBtn.setClickable(true)
            true
        }
    }
}