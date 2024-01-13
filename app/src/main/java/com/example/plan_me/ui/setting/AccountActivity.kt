package com.example.plan_me.ui.setting

import android.app.Activity
import android.app.Dialog
import android.content.Intent
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

        binding.accountNicknameBtn.setOnClickListener {
            switchActivity(ChangeNicknameActivity())
        }

        binding.accountSocialBtn.setOnClickListener {
            switchActivity(ChangeTypeActivity())
        }

        binding.accountLogoutTv.setOnClickListener {
            showDialog(DialogLogoutActivity(this@AccountActivity))
        }

        binding.accountDeleteTv.setOnClickListener {
            showDialog(DialogDeleteActivity(this@AccountActivity))
        }
    }

    private fun showDialog(dialog: Dialog) {
        dialog.show()
    }

    private fun switchActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun getData() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        userName = sharedPreferences.getString("userName", userName)
        userImg = sharedPreferences.getString("userImg", userImg)
        social = sharedPreferences.getString("social", social)
    }
}