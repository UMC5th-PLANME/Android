package com.example.plan_me

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}