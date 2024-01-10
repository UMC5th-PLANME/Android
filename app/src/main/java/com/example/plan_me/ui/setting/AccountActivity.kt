package com.example.plan_me.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityAccountBinding
import com.example.plan_me.ui.CircleTransform
import com.example.plan_me.ui.dialog.DialogDeleteActivity
import com.example.plan_me.ui.dialog.DialogLogoutActivity
import com.squareup.picasso.Picasso

class AccountActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    private var userName: String? = ""
    private var userImg: String? = ""
    private var social: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.accountNameTv.text = userName
        Picasso.get().load(userImg).transform(CircleTransform()).into(binding.accountImageIv)
        binding.accountSocialTypeTv.text = social

        binding.accountBackBtn.setOnClickListener {
            finish()
        }

        binding.accountLogoutTv.setOnClickListener {
            val dialog = DialogLogoutActivity(this@AccountActivity)
            dialog.show()
        }

        binding.accountDeleteTv.setOnClickListener {
            val dialog = DialogDeleteActivity(this@AccountActivity)
            dialog.show()
        }
    }

    private fun getData() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        userName = sharedPreferences.getString("userName", userName)
        userImg = sharedPreferences.getString("userImg", userImg)
        social = sharedPreferences.getString("social", social)
    }
}