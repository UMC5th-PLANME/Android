package com.example.plan_me.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityTimerSettingBinding
import com.google.android.material.tabs.TabLayoutMediator

class TimerSettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimerSettingBinding

    private val setting = arrayListOf("집중시간", "휴식시간", "반복횟수")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timerSettingAdapter = TimerVPAdapter(this)
        binding.timerTimeSettingVp.adapter = timerSettingAdapter

        TabLayoutMediator(binding.timerTimeSettingTl, binding.timerTimeSettingVp){
                tab, position ->
            tab.text = setting[position]
        }.attach()
    }

}