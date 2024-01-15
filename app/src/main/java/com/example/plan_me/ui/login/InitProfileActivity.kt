package com.example.plan_me.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.MainActivity
import com.example.plan_me.databinding.ActivityInitProfileBinding
import com.example.plan_me.ui.CircleTransform
import com.squareup.picasso.Picasso

class InitProfileActivity: AppCompatActivity() {
    private lateinit var binding: ActivityInitProfileBinding
    private var userName: String? = ""
    private var userImg: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.initProfileNameTv.text = userName
        Picasso.get().load(userImg).transform(CircleTransform()).into(binding.initProfileImagefileIv)
        binding.initProfileCompletBtn.setOnClickListener {
            goMainActivity()
        }
    }

    private fun getData() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        userName = sharedPreferences.getString("userName", userName)
        userImg = sharedPreferences.getString("userImg", userImg)
    }

    private fun goMainActivity() {
        val intent = Intent(this@InitProfileActivity, MainActivity::class.java)
        startActivity(intent)
    }
}