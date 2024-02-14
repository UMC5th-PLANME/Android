package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.plan_me.databinding.ActivityDialogBottomConsumerCenterBinding

class DialogBottomConsumerCenterActivity(context: Context): Dialog(context) {
    private lateinit var binding: ActivityDialogBottomConsumerCenterBinding
    private var customToast = CustomToast
    private var email = "planme.help@gmail.com"
    val clipboard: ClipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("email", email)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogBottomConsumerCenterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.bottomBtn.setOnClickListener {
            copy()
            dismiss()
        }
    }

    // 클립보드 복사
    private fun copy() {
        clipboard.setPrimaryClip(clip)
        context?.let { customToast.createToast(it,"클립보드에 복사되었습니다.", 300, true) }
    }
}