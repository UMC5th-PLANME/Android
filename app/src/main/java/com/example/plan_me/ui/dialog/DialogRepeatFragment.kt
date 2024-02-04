package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.plan_me.databinding.FragmentDialogRepeatBinding

class DialogRepeatFragment(context : Context, dialogRepeatInterface: DialogRepeatInterface):Dialog(context){
    private lateinit var binding : FragmentDialogRepeatBinding
    private lateinit var dialogRepeatInterface : DialogRepeatInterface

    init {
        this.dialogRepeatInterface = dialogRepeatInterface
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogRepeatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        clickListener()
    }
    private fun clickListener() {
        binding.dialogRepeatCancelBtn.setOnClickListener {
            dialogRepeatInterface?.onClickRepeatCancel()
        }
    }
}