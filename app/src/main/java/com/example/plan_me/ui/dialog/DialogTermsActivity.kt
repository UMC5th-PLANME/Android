package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.plan_me.databinding.ActivityDialogTermsBinding
import com.example.plan_me.ui.login.InitProfileActivity

class DialogTermsActivity(context: Context) : Dialog(context) {
    private lateinit var binding: ActivityDialogTermsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogTermsBinding.inflate(layoutInflater)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)

        binding.termsInfoCb.setOnCheckedChangeListener { _, _ ->
            allCheck()
        }

        binding.termsServiceCb.setOnCheckedChangeListener { _, _ ->
            allCheck()
        }

        binding.termsAllCb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.termsInfoCb.isChecked = true
                binding.termsServiceCb.isChecked = true
                binding.termsCompleteBtn.isEnabled = true
            } else {
                if (binding.termsInfoCb.isChecked) {
                    binding.termsInfoCb.isChecked = true
                    binding.termsServiceCb.isChecked = false
                    binding.termsCompleteBtn.isEnabled = false
                } else if (binding.termsServiceCb.isChecked) {
                    binding.termsInfoCb.isChecked = false
                    binding.termsServiceCb.isChecked = true
                    binding.termsCompleteBtn.isEnabled = false
                }
                binding.termsInfoCb.isChecked = false
                binding.termsServiceCb.isChecked = false
                binding.termsCompleteBtn.isEnabled = false
            }
        }

        binding.termsCompleteBtn.setOnClickListener {
            goInitProfileActivity()
        }
    }

    private fun allCheck() {
        if(binding.termsInfoCb.isChecked && binding.termsServiceCb.isChecked) {
            binding.termsAllCb.isChecked = true
            binding.termsCompleteBtn.isEnabled = true
        } else {
            binding.termsAllCb.isChecked = false
            binding.termsCompleteBtn.isEnabled = false
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