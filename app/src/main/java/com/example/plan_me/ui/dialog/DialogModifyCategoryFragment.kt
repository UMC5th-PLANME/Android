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
import com.example.plan_me.databinding.FragmentDialogModifyCategoryBinding
import com.example.plan_me.ui.main.MainActivity

class DialogModifyCategoryFragment(context : Context, private val categoryList: List<CategoryList>?, private val sendModifyMessage :DialogModifyFragment.SendModifyMessage):Dialog(context){
    private lateinit var binding : FragmentDialogModifyCategoryBinding
    private lateinit var adapter : ModifyCategoryRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogModifyCategoryBinding.inflate(layoutInflater)
        binding.dialogModifyCategoryRv
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogModifyCategoryCancelBtn.setOnClickListener {
            dismiss()
        }
        Log.d("list", categoryList.toString())

        if (categoryList != null) {
            val layoutManager = LinearLayoutManager(context)
            adapter = ModifyCategoryRVAdapter(context, categoryList, sendModifyMessage)
            binding.dialogModifyCategoryRv.layoutManager = layoutManager
            binding.dialogModifyCategoryRv.adapter = adapter
        }
    }
    //text
}