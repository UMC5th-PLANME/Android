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

class DialogTermsActivity(context: Context) : Dialog(context), TermsView {
    private lateinit var binding: ActivityDialogTermsBinding
    private var agree: IntArray = intArrayOf(1, 2)
    private var notAgree: IntArray = intArrayOf(1, 2)
    var member_id: Int? = 0
    var accessToken: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogTermsBinding.inflate(layoutInflater)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)

        binding.termsCompleteBtn.setOnClickListener {
            setTermsService()
        }
    }

    private fun isCheck() {
        if(binding.termsServiceCb.isChecked) {
            if(binding.termsInfoCb.isChecked) {
                binding.termsAllCb.isChecked = true
                agree = intArrayOf(1, 2)
                notAgree = intArrayOf(0)
            } else {
                binding.termsAllCb.isChecked = false
                agree = intArrayOf(1)
                notAgree = intArrayOf(2)
            }
        } else if (binding.termsInfoCb.isChecked) {
            if (!binding.termsServiceCb.isChecked) {
                binding.termsAllCb.isChecked = false
                agree = intArrayOf(2)
                notAgree = intArrayOf(1)
            }
        }
    }

    private fun getResData() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("getRes",
            AppCompatActivity.MODE_PRIVATE
        )
        member_id = sharedPreferences.getInt("member_id", member_id!!)
        accessToken = sharedPreferences.getString("getAccessToken", accessToken)
    }

    private fun setTermsService() {
        val setTermsService = MemberService()
        setTermsService.setTermsView(this@DialogTermsActivity)
        getResData()
        isCheck()
        Log.d("consent", "$member_id, $agree, $notAgree")
        val terms = Terms(member_id!!, agree, notAgree)
        setTermsService.setTerms(accessToken!!, terms)
        goInitProfileActivity()
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
}
