package com.example.plan_me.ui.setting

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plan_me.R
import com.example.plan_me.data.local.entity.EditProfile
import com.example.plan_me.data.remote.dto.auth.ChangeMemberRes
import com.example.plan_me.data.remote.dto.auth.MemberRes
import com.example.plan_me.data.remote.service.auth.MemberService
import com.example.plan_me.data.remote.view.auth.ChangeProfileView
import com.example.plan_me.data.remote.view.auth.LookUpMemberView
import com.example.plan_me.databinding.ActivityChangeNicknameBinding
import com.example.plan_me.ui.dialog.CustomToast

class ChangeNicknameFragment: Fragment(), ChangeProfileView, LookUpMemberView {
    private lateinit var binding: ActivityChangeNicknameBinding
    private var customToast = CustomToast
    private var userName : String? = ""
    private var accessToken: String? = ""
    private var userImg: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityChangeNicknameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.main_frm, AccountFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.changeNicknameBtn.setOnClickListener {
            setEditName()
            customToast.createToast(requireContext(),"닉네임이 변경되었습니다.", 300, true)
        }
    }

    private fun getData2() {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("getRes", MODE_PRIVATE)
        accessToken = sharedPreferences.getString("getAccessToken", accessToken)
    }

    private fun setEditName() {
        val setChangeProfileService = MemberService()
        setChangeProfileService.setChangeProfileView(this@ChangeNicknameFragment)
        val member = EditProfile(userName!!, userImg!!)
        setChangeProfileService.setChangeProfile("Bearer " + accessToken, member)
    }

    private fun setLookUpService() {
        val setLookUpService = MemberService()
        setLookUpService.setLookUpMemberView(this@ChangeNicknameFragment)
        setLookUpService.getLookUpMember("Bearer " + accessToken)
    }

    private fun updateUI() {
        binding.changeNicknameEt.setText(userName)
    }

    override fun onSetChangeProfileSuccess(response: ChangeMemberRes) {
        Log.d("닉네임 변경", response.result.toString())
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
            .replace(R.id.main_frm, AccountFragment())
            .addToBackStack(null)
            .commit()
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