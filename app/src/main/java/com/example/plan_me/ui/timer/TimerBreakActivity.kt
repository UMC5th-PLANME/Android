package com.example.plan_me.ui.timer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityTimerBreakBinding

class TimerBreakActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimerBreakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBreakBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}