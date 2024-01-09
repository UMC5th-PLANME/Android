package com.example.plan_me.ui.timer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityTimerFocusBinding

class TimerFocusActivity: AppCompatActivity() {
    private lateinit var binding: ActivityTimerFocusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerFocusBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}