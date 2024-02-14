package com.example.plan_me.ui.setting

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.R
import com.example.plan_me.data.local.entity.EditProfile
import com.example.plan_me.data.remote.dto.auth.ChangeMemberRes
import com.example.plan_me.data.remote.dto.auth.MemberRes
import com.example.plan_me.data.remote.service.auth.MemberService
import com.example.plan_me.data.remote.view.auth.ChangeProfileView
import com.example.plan_me.data.remote.view.auth.LookUpMemberView
import com.example.plan_me.databinding.ActivityChangeNicknameBinding
import com.example.plan_me.ui.dialog.CustomToast

class ChangeNicknameActivity: AppCompatActivity(), ChangeProfileView, LookUpMemberView {
    private lateinit var binding: ActivityChangeNicknameBinding
    private var customToast = CustomToast
    private var userName : String? = ""
    private var accessToken: String? = ""
    private var userImg: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.screen_start, R.anim.screen_none)

        getData2()
        setLookUpService()

        updateUI()

        userName = binding.changeNicknameEt.text.toString()

        binding.changeNicknameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // 입력 전에 호출되는 메서드
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때 호출되는 메서드
                userName = charSequence.toString()
                // 여기에서 변경된 텍스트에 대한 처리를 수행할 수 있습니다.
            }

            override fun afterTextChanged(editable: Editable?) {
                // 입력이 완료된 후에 호출되는 메서드
            }
        })

        binding.changeNicknameBackBtn.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
        }

        binding.changeNicknameBtn.setOnClickListener {
            setEditName()
            customToast.createToast(this@ChangeNicknameActivity,"닉네임이 변경되었습니다.", 300, true)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
    }

    private fun getData2() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("getRes", MODE_PRIVATE)
        accessToken = sharedPreferences.getString("getAccessToken", accessToken)
    }

    private fun setEditName() {
        val setChangeProfileService = MemberService()
        setChangeProfileService.setChangeProfileView(this@ChangeNicknameActivity)
        val member = EditProfile(userName!!, userImg!!)
        setChangeProfileService.setChangeProfile("Bearer " + accessToken, member)
    }

    private fun switchActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.screen_none, R.anim.screen_exit)
    }

    private fun setLookUpService() {
        val setLookUpService = MemberService()
        setLookUpService.setLookUpMemberView(this@ChangeNicknameActivity)
        setLookUpService.getLookUpMember("Bearer " + accessToken)
    }

    private fun updateUI() {
        binding.changeNicknameEt.setText(userName)
    }

    override fun onSetChangeProfileSuccess(response: ChangeMemberRes) {
        Log.d("닉네임 변경", response.result.toString())
        switchActivity(AccountActivity())
    }

    override fun onSetChangeProfileFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("닉네임 변경 실패", message)
    }

    override fun onGetMemberSuccess(response: MemberRes) {
        Log.d("닉네임 조회", response.result.toString())
        userName = response.result.nickname
        userImg = response.result.profile_image
        // UI 업데이트
        updateUI()
    }

    override fun onGetMemberFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("닉네임 조회 실패", message)
    }
}