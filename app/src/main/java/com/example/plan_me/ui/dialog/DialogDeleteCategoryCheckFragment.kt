package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.plan_me.databinding.ActivityDialogDeleteBinding
import com.example.plan_me.databinding.FragmentDialogCheckDeleteCategoryBinding

class DialogDeleteCategoryCheckActivity(context: Context, private val categoryId : Int): Dialog(context) {
    private lateinit var binding: FragmentDialogCheckDeleteCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogCheckDeleteCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogDeleteCategoryCheckCancelTv.setOnClickListener {
            dismiss()
        }
        binding.dialogDeleteCategoryCheckConfirmTv.setOnClickListener {

        }
    }
}