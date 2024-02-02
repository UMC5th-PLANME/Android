package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.R
import com.example.plan_me.data.local.entity.Terms
import com.example.plan_me.data.remote.dto.auth.TermsRes
import com.example.plan_me.data.remote.service.auth.MemberService
import com.example.plan_me.data.remote.view.auth.TermsView
import com.example.plan_me.databinding.ActivityDialogTermsBinding
import com.example.plan_me.ui.login.InitProfileActivity

class DialogTermsActivity(context: Context, val accessToken: String, val memberId: Int) : Dialog(context), TermsView {
    private lateinit var binding: ActivityDialogTermsBinding
    private var agree: List<Int> = listOf(0, 0)
    private var notAgree: List<Int> = listOf(0, 0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogTermsBinding.inflate(layoutInflater)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)

        if(binding.termsAllCb.isChecked) {
            binding.termsInfoCb.isChecked = true
            binding.termsServiceCb.isChecked = true
        }

        binding.termsCompleteBtn.setOnClickListener {
            setTermsService()
        }
    }

    private fun allCheck() {
        if(binding.termsInfoCb.isChecked && binding.termsServiceCb.isChecked) {
            binding.termsAllCb.isChecked = true
        }

        if(binding.termsAllCb.isChecked) {
            binding.termsInfoCb.isChecked = true
            binding.termsServiceCb.isChecked = true
        }
    }

    private fun isCheck() {
        if(binding.termsServiceCb.isChecked) {
            if(binding.termsInfoCb.isChecked) {
                binding.termsAllCb.isChecked = true
                agree = listOf(1, 2)
            } else {
                binding.termsAllCb.isChecked = false
                agree = listOf(1)
                notAgree = listOf(2)
            }
        } else if (binding.termsInfoCb.isChecked) {
            if (!binding.termsServiceCb.isChecked) {
                binding.termsAllCb.isChecked = false
                agree = listOf(2)
                notAgree = listOf(1)
            }
        }
    }

    private fun setTermsService() {
        val setTermsService = MemberService()
        setTermsService.setTermsView(this@DialogTermsActivity)
        isCheck()

        val terms = Terms(memberId, agree, notAgree)
        setTermsService.setTerms(accessToken, terms)
    }

    private fun goInitProfileActivity() {
        val intent = Intent(context, InitProfileActivity::class.java)
        context.startActivity(intent)
    }

    override fun onSetTermsSuccess(response: TermsRes) {
        Log.d("약관 동의", response.message)
        goInitProfileActivity()
    }

    override fun onSetTermsFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("약관 동의 실패", message)
    }

    companion object {
        private const val DEFAULT_MEMBER_ID = 0 // 초기값 설정
        private const val DEFAULT_ACCESS_TOKEN = "" // 초기값 설정
    }
}
