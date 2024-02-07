package com.example.plan_me.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.databinding.FragmentDialogAlarmBinding
import com.example.plan_me.databinding.FragmentDialogDeleteCategoryBinding
import com.example.plan_me.ui.main.MainActivity

class DialogDeleteCategoryFragment(context : Context, private val categoryList: List<CategoryList>, private val sendDeleteMessage: DialogDeleteCategoryCheckFragment.SendDeleteMessage):Dialog(context){
    private lateinit var binding : FragmentDialogDeleteCategoryBinding
    private lateinit var adapter : DeleteCategoryRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogDeleteCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogDeleteCategoryCancelBtn.setOnClickListener {
            dismiss()
        }
        Log.d("list", categoryList.toString())

        val layoutManager = LinearLayoutManager(context)
        adapter = DeleteCategoryRVAdapter(context, categoryList, sendDeleteMessage)
        binding.dialogDeleteCategoryRv.layoutManager = layoutManager
        binding.dialogDeleteCategoryRv.adapter = adapter
    }
    //text
}