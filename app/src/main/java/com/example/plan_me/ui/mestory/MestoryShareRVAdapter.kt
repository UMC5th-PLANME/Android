package com.example.plan_me.ui.mestory

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.R
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.mestory.GetTimeResult
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.view.category.AllCategoryView
import com.example.plan_me.databinding.ItemMestoryCategoryOpenBinding
import com.example.plan_me.databinding.ItemMestoryShareBinding
import com.example.plan_me.ui.all.Daily.ScheduleRVAdapter

class MestoryShareRVAdapter(private val categoryList : List<CategoryList>, private val scheduleMap: MutableMap<Int, Int>, private val context: Context): RecyclerView.Adapter<MestoryShareRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MestoryShareRVAdapter.ViewHolder {
        val binding: ItemMestoryShareBinding = ItemMestoryShareBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        //mestoryColor()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MestoryShareRVAdapter.ViewHolder, position: Int) {
        if (!scheduleMap.isNullOrEmpty()) {
            holder.bind(categoryList[position], scheduleMap)
        }
    }

    override fun getItemCount(): Int = categoryList.size

    inner class ViewHolder(val binding: ItemMestoryShareBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (category: CategoryList, scheduleMap: MutableMap<Int, Int>) {


            if (scheduleMap[category.categoryId] != null) {
                binding.mestoryCategoryEmoticonTv.text = category.emoticon
                binding.mestoryCategoryTv.text = category.name
                val colorCode = ContextCompat.getColor(context, category.color)
                binding.mestoryItemOpenedCv.setCardBackgroundColor(colorCode)
            }

            binding.mestoryCategoryTv.isSelected = true
            binding.mestoryCategoryProgressBar.progress =  scheduleMap[category.categoryId]!!
            binding.mestoryCategoryProgressPercentageTv.text = scheduleMap[category.categoryId]!!.toString() + "%"
        }
    }

}
