package com.example.plan_me

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.plan_me.databinding.FragmentDialogAlarmBinding
import com.example.plan_me.databinding.FragmentDialogRepeatBinding

class DialogAlarmFragment(context : Context):Dialog(context){
    private lateinit var binding : FragmentDialogAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogAlarmCancelBtn.setOnClickListener {
            dismiss()
        }
    }

}