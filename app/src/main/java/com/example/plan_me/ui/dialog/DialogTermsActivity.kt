package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.plan_me.R
import com.example.plan_me.data.remote.dto.auth.TermsRes
import com.example.plan_me.data.remote.view.auth.TermsView
import com.example.plan_me.databinding.ActivityDialogTermsBinding
import com.example.plan_me.ui.login.InitProfileActivity

class DialogTermsActivity(context: Context) : Dialog(context), TermsView {
    private lateinit var binding: ActivityDialogTermsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogTermsBinding.inflate(layoutInflater)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)

        binding.termsCompleteBtn.setOnClickListener {
            goInitProfileActivity()
        }
    }

    private fun isCheck() {
        if(binding.termsServiceCb.isChecked) {

        }
    }

    private fun goInitProfileActivity() {
        val intent = Intent(context, InitProfileActivity::class.java)
        context.startActivity(intent)
    }

    override fun onSetTermsSuccess(response: TermsRes) {
        Log.d("약관 동의", response.message)
    }

    override fun onSetTermsFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("약관 동의 실패", message)
    }
}
