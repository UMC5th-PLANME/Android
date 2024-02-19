package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.category.DeleteCategoryRes
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.view.category.DeleteCategoryView
import com.example.plan_me.databinding.FragmentDialogCheckDeleteCategoryBinding

class DialogDeleteCategoryCheckFragment(context: Context, private val category: CategoryList, private val sendDeleteMessage: SendDeleteMessage, private val position: Int): Dialog(context), DeleteCategoryView{
    private lateinit var binding: FragmentDialogCheckDeleteCategoryBinding

    interface SendDeleteMessage {
        fun sendDeleteMessage(category: CategoryList)
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
        val access_token = "Bearer " + context.getSharedPreferences("getRes",
            AppCompatActivity.MODE_PRIVATE
        ).getString("getAccessToken", "")

        Log.d("accessToken", access_token)

        val setCategoryService = CategoryService()
        setCategoryService.setDeleteCategoryView(this)
        setCategoryService.deleteCategoryFun(access_token!!, category.categoryId)
    }

    override fun onDeleteCategorySuccess(response: DeleteCategoryRes) {
        dismiss()
        sendDeleteMessage.sendDeleteMessage(category)
    }

    override fun onDeleteCategoryFailure(response: DeleteCategoryRes) {
        dismiss()
    }
}