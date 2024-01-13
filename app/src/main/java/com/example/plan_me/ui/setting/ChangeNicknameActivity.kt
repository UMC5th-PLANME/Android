package com.example.plan_me.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityChangeNicknameBinding

class ChangeNicknameActivity: AppCompatActivity() {
    private lateinit var binding: ActivityChangeNicknameBinding
    private var userName : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.changeNicknameEt.setText(userName)

        binding.changeNicknameBackBtn.setOnClickListener {
            finish()
        }

        binding.changeNicknameBtn.setOnClickListener {
            // 임시 설정
            Toast.makeText(this@ChangeNicknameActivity, "변경되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun getData() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        userName = sharedPreferences.getString("userName", userName)
    }
}