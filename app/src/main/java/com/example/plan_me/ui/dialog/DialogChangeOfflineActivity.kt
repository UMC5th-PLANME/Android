package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.databinding.ActivityDialogChangeOfflineBinding

class DialogChangeOfflineActivity(context: Context): Dialog(context) {
    private lateinit var binding: ActivityDialogChangeOfflineBinding
    private var social: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogChangeOfflineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.changeOfflineCancelBtn.setOnClickListener {
            dismiss()
        }

        binding.changeOfflineSaveBtn.setOnClickListener {
            // 임시 설정
            social = "오프라인"
            saveData()
            Toast.makeText(context, "오프라인 계정으로 전환되었습니다.", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun saveData() {
        // 데이터 저장
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("userInfo",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("social", social)
        editor.apply()
    }
}