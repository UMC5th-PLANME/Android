package com.example.plan_me

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.plan_me.databinding.ActivityTimerBinding

class TimerActivity : AppCompatActivity(){

    private lateinit var binding: ActivityTimerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val timerAdapter = TimerVPAdapter(this)
        binding.timerViewpager.adapter = timerAdapter
        binding.timerViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.timerViewpagerIndicator.setViewPager(binding.timerViewpager)

    }

}

