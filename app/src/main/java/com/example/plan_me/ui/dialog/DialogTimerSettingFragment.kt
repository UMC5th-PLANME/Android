package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.plan_me.databinding.FragmentDialogTimerSettingBinding

class DialogTimerSettingFragment(context : Context): Dialog(context) {
    private lateinit var binding: FragmentDialogTimerSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogTimerSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // (FOCUS/BREAK 타이머 설정) 취소 버튼 눌렀을 때
        binding.dialogTimeSettingCancel.setOnClickListener{
            dismiss()
        }

        // (FOCUS/BREAK 타이머 설정) 확인 버튼 눌렀을 때
        binding.dialogTimeSettingConfirm.setOnClickListener{

        }
    }
}