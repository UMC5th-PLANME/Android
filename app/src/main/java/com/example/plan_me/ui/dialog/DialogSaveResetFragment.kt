package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.plan_me.databinding.FragmentDialogSaveResetBinding

class DialogSaveResetFragment(context : Context): Dialog(context) {
    private lateinit var binding : FragmentDialogSaveResetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogSaveResetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        // 취소 버튼 눌렀을 때 ME 스토리 기록 X
        binding.timerDialogCancelResetTv.setOnClickListener {
            dismiss()
        }

        // 확인 버튼 눌렀을 때 ME 스토리에 기록
        binding.timerDialogSaveResetTv.setOnClickListener {



        }
    }
}