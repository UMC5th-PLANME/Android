package com.example.plan_me.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.plan_me.MainActivity
import com.example.plan_me.databinding.ActivityInitProfileBinding

class InitProfileActivity: AppCompatActivity() {
    private lateinit var binding: ActivityInitProfileBinding
    private var userName: String = ""
    private var userImg: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra("userName")!!
        userImg = intent.getStringExtra("userImg")!!

        binding.initProfileNameTv.text = userName
        Glide.with(this).load(userImg).into(binding.initProfileImagefileIv)

        binding.initProfileCompletBtn.setOnClickListener {
            goMainActivity()
        }
    }

    private fun goMainActivity() {
        val intent = Intent(this@InitProfileActivity, MainActivity::class.java)
        // setting 화면에서 사용
        intent.putExtra("userName", userName)
        intent.putExtra("userImg", userImg)
        startActivity(intent)
    }
}