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

class DialogTermsActivity(context: Context) : Dialog(context) {
    private lateinit var binding: ActivityDialogTermsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogTermsBinding.inflate(layoutInflater)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)

        //allCheck()

        binding.termsCompleteBtn.setOnClickListener {
            goInitProfileActivity()
        }
    }

    override fun onStart() {
        super.onStart()
        allCheck()
    }

    private fun allCheck() {
        if(binding.termsInfoCb.isChecked && binding.termsServiceCb.isChecked) {
            binding.termsAllCb.isChecked = true
            binding.termsCompleteBtn.isEnabled = true
        }
    }

    private fun goInitProfileActivity() {
        val intent = Intent(context, InitProfileActivity::class.java)
        context.startActivity(intent)
    }

    companion object {
        private const val DEFAULT_MEMBER_ID = 0 // 초기값 설정
        private const val DEFAULT_ACCESS_TOKEN = "" // 초기값 설정
    }
}
