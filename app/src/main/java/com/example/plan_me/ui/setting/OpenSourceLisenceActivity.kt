package com.example.plan_me.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityOpensourceLisenceBinding

class OpenSourceLisenceActivity: AppCompatActivity() {
    private lateinit var binding: ActivityOpensourceLisenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpensourceLisenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.opensourceLisenceBackBtn.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}