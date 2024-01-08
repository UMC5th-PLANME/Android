package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.plan_me.MainActivity
import com.example.plan_me.databinding.ActivityDialogTermsBinding

class DialogTermsActivity(context: Context): Dialog(context) {
    private lateinit var binding: ActivityDialogTermsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogTermsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

}