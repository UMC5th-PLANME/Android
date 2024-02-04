package com.example.plan_me.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.R
import com.example.plan_me.data.local.entity.Member
import com.example.plan_me.data.remote.dto.auth.ChangeMemberRes
import com.example.plan_me.data.remote.service.auth.MemberService
import com.example.plan_me.data.remote.view.auth.ChangeProfileView
import com.example.plan_me.databinding.ActivityChangeNicknameBinding

class ChangeNicknameActivity: AppCompatActivity(), ChangeProfileView {
    private lateinit var binding: ActivityChangeNicknameBinding
    private var userName : String? = ""
    private var accessToken: String? = ""
    private var userImg: String? = ""
    private var userType: String? = ""
    private var userEmail: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)

        getData1()
        getData2()

        binding.changeNicknameEt.setText(userName)
        userName = binding.changeNicknameEt.text.toString()
        saveData()

        binding.changeNicknameBackBtn.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }

        binding.changeNicknameBtn.setOnClickListener {
            setEditName()
            Toast.makeText(this@ChangeNicknameActivity, "변경되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
    }

    private fun getData1() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        userName = sharedPreferences.getString("userName", userName)
        userImg = sharedPreferences.getString("userImg", userImg)
        userType = sharedPreferences.getString("social", userType)
        userEmail = sharedPreferences.getString("email", userEmail)
    }

    private fun getData2() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("getRes", MODE_PRIVATE)
        accessToken = sharedPreferences.getString("getAccessToken", accessToken)
    }

    // nickname update
    private fun saveData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("userName", userName)
    }

    private fun setEditName() {
        val setChangeProfileService = MemberService()
        setChangeProfileService.setChangeProfileView(this@ChangeNicknameActivity)
        val member = Member(userName!!, userImg!!, userType!!, userEmail!!)
        setChangeProfileService.setChangeProfile(accessToken!!, member)
    }

    override fun onSetChangeProfileSuccess(response: ChangeMemberRes) {
        Log.d("닉네임 변경", response.result.toString())
    }

    override fun onSetChangeProfileFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("닉네임 변경 실패", message)
    }
}