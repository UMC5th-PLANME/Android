package com.example.plan_me.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityProducerBinding

class ProducerActivity: AppCompatActivity() {
    private lateinit var binding: ActivityProducerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProducerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.producerBackBtn.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}