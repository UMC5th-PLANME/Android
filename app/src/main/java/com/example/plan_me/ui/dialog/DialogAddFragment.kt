package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.data.local.entity.Category_input
import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.view.category.AddCategoryView
import com.example.plan_me.databinding.FragmentDialogAddCategoryBinding

class DialogAddFragment(context : Context, private val sendSignalToMain: SendSignalToMain): Dialog(context), AddCategoryView {
    private lateinit var binding : FragmentDialogAddCategoryBinding
    private var ignoreCheckChange = false

    interface SendSignalToMain {
        fun sendSuccessSignal()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.addCategoryCancelBtn.setOnClickListener {
            dismiss()
        }
        binding.addCategorySaveBtn.setOnClickListener {//연결 완료
            val access_token = "Bearer " + context.getSharedPreferences("getRes", AppCompatActivity.MODE_PRIVATE).getString("getAccessToken", "")
            Log.d("access token", access_token)
            val setCategoryService = CategoryService()
            setCategoryService.setAddCategoryView(this)
            val categoryInput = Category_input("Study", "note", 2131034742)
            setCategoryService.addCategoryFun(access_token!!, categoryInput)
        }

        addPopup(binding.emoticonGroup1, binding.emoticonGroup2)
        addPopup(binding.colorGroup1, binding.colorGroup2)

    }
    private fun addPopup(radioGroup1: RadioGroup, radioGroup2: RadioGroup) {

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

    override fun onAddCategorySuccess(response: AddCategoryRes) {
        Log.d("response", response.toString())
        sendSignalToMain.sendSuccessSignal()
        dismiss()
    }

    override fun onAddCategoryFailure(response: AddCategoryRes) {
        Log.d("response", response.toString())
        dismiss()
    }
}