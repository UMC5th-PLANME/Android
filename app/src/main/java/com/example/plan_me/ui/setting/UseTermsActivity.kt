package com.example.plan_me.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityUseTermsBinding

class UseTermsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityUseTermsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUseTermsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.useTermsBackBtn.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}