package com.example.plan_me.ui.setting

import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.R
import com.example.plan_me.databinding.ActivityChangeTypeBinding
import com.example.plan_me.ui.dialog.DialogChangeOfflineActivity
import com.example.plan_me.ui.dialog.DialogMaintainProflieActivity

class ChangeTypeActivity: AppCompatActivity() {
    private lateinit var binding: ActivityChangeTypeBinding
    private var social: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.changeTypeBackBtn.setOnClickListener {
            finish()
        }

        binding.changeTypeEt.setText(social)

        if(social == "카카오" || social == "구글") {
            binding.changeTypeAccountTv.setText(R.string.offline)
            binding.changeTypeAccountBtn.setOnClickListener {
                showDialog(DialogChangeOfflineActivity(this@ChangeTypeActivity))
            }
        } else {
            binding.changeTypeAccountTv.setText(R.string.change_account_type)
            binding.changeTypeAccountBtn.setOnClickListener {
                showDialog(DialogMaintainProflieActivity(this@ChangeTypeActivity))
            }
        }
        Log.d("social type", social!!)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun showDialog(dialog: Dialog) {
        dialog.show()
    }

    private fun getData() {
        // 데이터 읽어오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        social = sharedPreferences.getString("social", social)
    }
}