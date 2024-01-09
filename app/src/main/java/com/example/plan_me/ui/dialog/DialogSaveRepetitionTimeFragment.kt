package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.plan_me.databinding.FragmentDialogSaveFocusTimeBinding

class DialogSaveRepetitionTimeFragment(context : Context): Dialog(context) {
    private lateinit var binding : FragmentDialogSaveFocusTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogSaveFocusTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // N분 집중 성공 -> 여기서 N을 바꿔야 하는데... 어떻게...?


        // 취소 버튼 눌렀을 때 ME 스토리 기록 X
        binding.timerDialogSaveCancelTimeTv.setOnClickListener {
            dismiss()
        }

        // 확인 버튼 눌렀을 때 ME 스토리에 기록
        binding.timerDialogSaveFocusTimeTv.setOnClickListener {

        }
    }
}