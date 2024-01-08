package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.plan_me.R
import com.example.plan_me.databinding.FragmentDialogAddCategoryBinding
import com.example.plan_me.databinding.FragmentDialogSaveFocusTimeBinding

class DialogAddFragment(context : Context): Dialog(context) {
    private lateinit var binding : FragmentDialogAddCategoryBinding
    private var ignoreCheckChange = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.addCategoryCancelBtn.setOnClickListener {
            dismiss()
        }

    }
    private fun addPopup() {
        val radioGroup1 = binding.colorGroup1
        val radioGroup2 = binding.colorGroup1

        // 라디오 그룹1 체크 상태 변경 리스너
        radioGroup1!!.setOnCheckedChangeListener { group, checkedId ->
            if (!ignoreCheckChange) {
                ignoreCheckChange = true
                // 라디오 그룹2의 체크 여부 확인
                val isCheckedInGroup2 = radioGroup2!!.checkedRadioButtonId != -1
                if (isCheckedInGroup2) {
                    // 라디오 그룹2에 체크된 버튼이 있는 경우
                    radioGroup2!!.clearCheck()
                }
                ignoreCheckChange = false
            }
        }

        // 라디오 그룹2 체크 상태 변경 리스너
        radioGroup2!!.setOnCheckedChangeListener { group, checkedId ->
            if (!ignoreCheckChange) {
                ignoreCheckChange = true
                // 라디오 그룹1의 체크 여부 확인
                val isCheckedInGroup1 = radioGroup1!!.checkedRadioButtonId != -1
                if (isCheckedInGroup1) {
                    // 라디오 그룹1에 체크된 버튼이 있는 경우
                    radioGroup1!!.clearCheck()
                }
                ignoreCheckChange = false
            }
        }

    }
}