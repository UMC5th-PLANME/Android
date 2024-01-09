package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.plan_me.databinding.ActivityDialogTermsBinding
import com.example.plan_me.ui.login.InitProfileActivity

class DialogTermsActivity(context: Context): Dialog(context) {
    private lateinit var binding: ActivityDialogTermsBinding
    var userName: String = ""
    var userImg: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogTermsBinding.inflate(layoutInflater)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.termsCompletBtn.setOnClickListener {
            goInitProfileActivity()
        }
        setContentView(binding.root)
    }

    private fun goInitProfileActivity() {
        val intent = Intent(context, InitProfileActivity::class.java)
        intent.putExtra("userName", userName)
        intent.putExtra("userImg", userImg)
        context.startActivity(intent)
    }
}