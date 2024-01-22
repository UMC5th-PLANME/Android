package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityDialogTermsBinding
import com.example.plan_me.ui.login.InitProfileActivity

class DialogTermsActivity(context: Context) : Dialog(context) {
    private lateinit var binding: ActivityDialogTermsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogTermsBinding.inflate(layoutInflater)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)

        clickBtn()

        binding.termsCompleteBtn.setOnClickListener {
            goInitProfileActivity()
        }
    }

    private fun clickBtn() {
        binding.termsSerivceBtn.setOnClickListener {
            if (binding.termsServiceTextLo.visibility == View.GONE) {
                binding.termsServiceTextLo.visibility = View.VISIBLE
                binding.termsSerivceBtn.setBackgroundResource(R.drawable.mestory_category_up)
            } else {
                binding.termsServiceTextLo.visibility = View.GONE
                binding.termsSerivceBtn.setBackgroundResource(R.drawable.mestory_category_down)
            }
        }

        binding.termsInfoBtn.setOnClickListener {
            if (binding.termsInfoTextLo.visibility == View.GONE) {
                binding.termsInfoTextLo.visibility = View.VISIBLE
                binding.termsInfoBtn.setBackgroundResource(R.drawable.mestory_category_up)
            } else {
                binding.termsInfoTextLo.visibility = View.GONE
                binding.termsInfoBtn.setBackgroundResource(R.drawable.mestory_category_down)
            }
        }
    }

    private fun goInitProfileActivity() {
        val intent = Intent(context, InitProfileActivity::class.java)
        context.startActivity(intent)
    }
}
