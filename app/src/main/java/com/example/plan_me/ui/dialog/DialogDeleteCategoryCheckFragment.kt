package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.example.plan_me.data.remote.dto.category.DeleteCategoryRes
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.view.category.DeleteCategoryView
import com.example.plan_me.databinding.FragmentDialogCheckDeleteCategoryBinding

class DialogDeleteCategoryCheckFragment(context: Context, private val categoryId : Int, private val sendDeleteMessage: SendDeleteMessage): Dialog(context), DeleteCategoryView{
    private lateinit var binding: FragmentDialogCheckDeleteCategoryBinding

    interface SendDeleteMessage {
        fun sendDeleteMessage()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogCheckDeleteCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogDeleteCategoryCheckCancelTv.setOnClickListener {
            dismiss()
        }
        binding.dialogDeleteCategoryCheckConfirmTv.setOnClickListener {
            DeleteCategory()
        }
    }
    private fun DeleteCategory() {
        val access_token = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWt1blRlc3QiLCJyb2xlIjoiVVNFUiIsImlhdCI6MTcwNzA0NDA4NCwiZXhwIjoxNzA3MDUxMjg0fQ.hQe_ChRIMBDhkIhcpx7H0vo53aUSdxAAE11ZUuoaZVs"
        val setCategoryService = CategoryService()
        setCategoryService.setDeleteCategoryView(this)
        setCategoryService.deleteCategoryFun(access_token!!, categoryId)
    }

    override fun onDeleteCategorySuccess(response: DeleteCategoryRes) {
        dismiss()
        sendDeleteMessage.sendDeleteMessage()
    }

    override fun onDeleteCategoryFailure(response: DeleteCategoryRes) {
        dismiss()
    }
}