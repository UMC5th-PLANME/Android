package com.example.plan_me.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityProcessPoliciesBinding

class ProcessPoliciesActivity: AppCompatActivity() {
    private lateinit var binding: ActivityProcessPoliciesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcessPoliciesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.processPoliciesBackBtn.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}