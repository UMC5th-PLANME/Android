package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.example.plan_me.data.remote.dto.auth.DeleteMemberRes
import com.example.plan_me.data.remote.service.auth.MemberService
import com.example.plan_me.data.remote.view.auth.DeleteMemberView
import com.example.plan_me.databinding.ActivityDialogDeleteBinding
import com.example.plan_me.ui.login.LoginActivity

class DialogDeleteActivity(context: Context, val accessToken: String): Dialog(context), DeleteMemberView {
    private lateinit var binding: ActivityDialogDeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.deleteCancelBtn.setOnClickListener {
            dismiss()
        }

        binding.deleteSaveBtn.setOnClickListener {
            setDeleteService()
        }
    }

    private fun setDeleteService() {
        val setDeleteService = MemberService()
        setDeleteService.setDeleteMemberView(this@DialogDeleteActivity)
        setDeleteService.delMember("Bearer " + accessToken)
    }

    private fun navigateToFirstScreen() {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)

        dismiss()
    }

    override fun onDelMemberSuccess(response: DeleteMemberRes) {
        Log.d(" 회원 탈퇴", response.result.toString())
        navigateToFirstScreen()
    }

    override fun onDelMemberFailure(isSuccess: Boolean, code: String, message: String) {
        Log.d("회원 탈퇴 실패", message)
    }
}