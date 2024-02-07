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
import com.example.plan_me.databinding.FragmentDialogSelectCategoryBinding
import com.example.plan_me.ui.main.MainActivity

class DialogSelectCategoryFragment(context : Context, private val categoryList: ArrayList<CategoryList>, private val dialogSelectCategoryInerface: DialogSelectCategoryInerface):Dialog(context){
    private lateinit var binding : FragmentDialogSelectCategoryBinding
    private lateinit var adapter : SelectCategoryRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogSelectCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogSelectCategoryCancelBtn.setOnClickListener {
            dismiss()
        }
        Log.d("list", categoryList.toString())

        val layoutManager = LinearLayoutManager(context)
        adapter = SelectCategoryRVAdapter(context, categoryList, dialogSelectCategoryInerface)
        binding.dialogSelectCategoryRv.layoutManager = layoutManager
        binding.dialogSelectCategoryRv.adapter = adapter
    }
    //text
}