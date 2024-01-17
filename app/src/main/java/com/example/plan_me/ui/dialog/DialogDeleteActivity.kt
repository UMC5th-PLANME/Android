package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.plan_me.databinding.ActivityDialogDeleteBinding

class DialogDeleteActivity(context: Context): Dialog(context) {
    private lateinit var binding: ActivityDialogDeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.deleteCancelBtn.setOnClickListener {
            dismiss()
        }
    }
}