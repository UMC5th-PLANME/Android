package com.example.plan_me.ui.timer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
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
        binding.timerTimeSettingVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.timerTimeSettingVp.isUserInputEnabled = false

        TabLayoutMediator(binding.timerTimeSettingTl, binding.timerTimeSettingVp){
                tab, position ->
            tab.text = setting[position]
        }.attach()

        clickListener()
    }

    private fun clickListener() {
        binding.informationBackBtn.setOnClickListener {
            // TimerFocusActivity 로 이동
            val intent = Intent(this, TimerFocusActivity::class.java)
            startActivity(intent)
        }
    }


}