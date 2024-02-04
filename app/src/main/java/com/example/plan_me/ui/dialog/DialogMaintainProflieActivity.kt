package com.example.plan_me.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.plan_me.databinding.ActivityDialogMaintainProfileBinding
import com.example.plan_me.ui.login.LoginActivity

class DialogMaintainProflieActivity(context: Context) : Dialog(context) {
    private lateinit var binding: ActivityDialogMaintainProfileBinding
    private var social: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogMaintainProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.maintainProfileCancelBtn.setOnClickListener {
            // 임시 설정
            initData()
            switchActivity(LoginActivity())
        }

        binding.maintainProfileSaveBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun switchActivity(activity: Activity) {
        val intent = Intent(context, activity::class.java)
        context.startActivity(intent)
    }

    private fun initData() {
        // 데이터 초기화
        val sharedPreferences = context.getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}