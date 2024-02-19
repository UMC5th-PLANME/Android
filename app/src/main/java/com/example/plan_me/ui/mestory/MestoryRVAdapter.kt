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
import com.example.plan_me.ui.all.Daily.ScheduleRVAdapter

class MestoryRVAdapter(private val categoryList : List<CategoryList>, private val scheduleMap: MutableMap<Int, MutableList<ScheduleList>>, private val context: Context): RecyclerView.Adapter<MestoryRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MestoryRVAdapter.ViewHolder {
        val binding: ItemMestoryCategoryOpenBinding = ItemMestoryCategoryOpenBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        //mestoryColor()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MestoryRVAdapter.ViewHolder, position: Int) {
        if (!scheduleMap.isNullOrEmpty()) {
            holder.bind(categoryList[position], scheduleMap)
        }
    }

    override fun getItemCount(): Int = categoryList.size

    inner class ViewHolder(val binding: ItemMestoryCategoryOpenBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (category: CategoryList, scheduleMap: MutableMap<Int, MutableList<ScheduleList>>) {

            val count = scheduleMap[category.categoryId]!!.count { it.status }
            val notyet = scheduleMap[category.categoryId]!!.size - count

            if (!scheduleMap[category.categoryId].isNullOrEmpty()) {
                binding.mestoryCategoryEmoticonTv.text = category.emoticon
                binding.mestoryCategoryTv.text = category.name
                binding.mestoryCategoryDetailTv.text = count.toString() + " completed • "+notyet.toString() + " not yet"
                val colorCode = ContextCompat.getColor(context, category.color)
                binding.mestoryItemOpenedCv.setCardBackgroundColor(colorCode)
            }

            binding.mestoryCategoryTv.isSelected = true

            binding.mestoryCategoryDownBt.setOnClickListener {
                binding.mestroyProgressLayout.visibility = View.VISIBLE
                binding.mestoryCategoryUpBt.visibility = View.VISIBLE
                binding.mestoryCategoryDownBt.visibility = View.GONE
            }
            binding.mestoryCategoryUpBt.setOnClickListener {
                binding.mestoryCategoryUpBt.visibility = View.GONE
                binding.mestroyProgressLayout.visibility = View.GONE
                binding.mestoryCategoryDownBt.visibility = View.VISIBLE
                binding.mestoryCategoryDetailTv.text = count.toString() + " completed • "+notyet.toString() + " not yet"
            }
            binding.mestoryCategoryProgressBar.progress =  ((count.toFloat()/ scheduleMap[category.categoryId]!!.size.toFloat()) * 100).toInt()
            binding.mestoryCategoryProgressPercentageTv.text = ((count.toFloat()/ scheduleMap[category.categoryId]!!.size.toFloat()) * 100).toInt().toString() + "%"
        }
    }

}
