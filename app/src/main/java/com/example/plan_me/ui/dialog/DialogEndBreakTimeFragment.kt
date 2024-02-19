package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.plan_me.databinding.FragmentDialogEndBreakTimeBinding
import com.example.plan_me.databinding.FragmentDialogSaveFocusTimeBinding

class DialogEndBreakTimeFragment(context : Context): Dialog(context) {
    private lateinit var binding : FragmentDialogEndBreakTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogEndBreakTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        // 취소 버튼 눌렀을 때 -> FocusTimer 로 이동 -> 타이머 시작 X
        binding.timerDialogNotFocusTimeTv.setOnClickListener {
            dismiss()
        }

        // 확인 버튼 눌렀을 때 -> FocusTimer 로 이동 -> 타이머 바로 시작
        binding.timerDialogNotFocusTimeTv.setOnClickListener {
            dismiss()
        }
    }
}