package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.databinding.FragmentDialogTimerCategoryBinding
import com.example.plan_me.ui.main.MainDrawerRVAdapter

class DialogTimerCategoryFragment(context : Context, private val categoryList: List<CategoryList>, private val sendData: SendData): Dialog(context), MainDrawerRVAdapter.SendClickCategory {
    private lateinit var binding: FragmentDialogTimerCategoryBinding
    private lateinit var adapter: TimerCategoryRVAdapter

    interface SendData{
        fun sendData(category: CategoryList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogTimerCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogDeleteCategoryCancelBtn.setOnClickListener {
            dismiss()
        }

        if (categoryList != null) {
            val layoutManager = LinearLayoutManager(context)
            adapter = TimerCategoryRVAdapter(context, categoryList, this)
            binding.dialogDeleteCategoryRv.layoutManager = layoutManager
            binding.dialogDeleteCategoryRv.adapter = adapter
        }
    }

    override fun sendClickCategory(category: CategoryList, position: Int) {
        sendData.sendData(category)
    }
}