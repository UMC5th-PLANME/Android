package com.example.plan_me.ui.dialog

import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.databinding.ItemScheduleBinding
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.ui.all.Daily.ScheduleRVAdapter

class DialogCalendarBtmRVAdapter(private val categoryList : List<CategoryList>, private val scheduleMap: MutableMap<Int, MutableList<ScheduleList>>, private val context: Context, private val sendSignalModify: ScheduleRVAdapter.SendSignalModify) : RecyclerView.Adapter<DialogCalendarBtmRVAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemScheduleBinding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!scheduleMap.isNullOrEmpty()) {
            holder.bind(categoryList[position], scheduleMap)
        }
    }

    override fun getItemCount(): Int = categoryList.size

    inner class ViewHolder(val binding: ItemScheduleBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryList, scheduleMap: MutableMap<Int, MutableList<ScheduleList>>) {
            if (!scheduleMap[category.categoryId].isNullOrEmpty()) {
                val count = scheduleMap[category.categoryId]!!.count { it.status }
                val notyet = scheduleMap[category.categoryId]!!.size - count
                binding.itemScheduleTitleTv.text = category.name
                binding.itemScheduleEmoticonTv .text= category.emoticon
                binding.itemScheduleDetail.text = count.toString() + " completed • "+notyet.toString() + " not yet"  //수정해야함
                val newColor = ContextCompat.getColor(context, category.color) // Replace with your desired color resource
                binding.itemScheduleView.background.setColorFilter(newColor, PorterDuff.Mode.SRC_IN)

                    val dailyRVAdapter = ScheduleRVAdapter(categoryList, scheduleMap[category.categoryId], context, sendSignalModify)
                    binding.itemScheduleRv.adapter = dailyRVAdapter
                    binding.itemScheduleRv.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

            binding.itemScheduleMore.setOnClickListener {
                binding.itemScheduleRv.visibility = View.VISIBLE
                binding.itemScheduleFlip.visibility = View.VISIBLE
                binding.itemScheduleMore.visibility = View.GONE
                binding.itemScheduleDetail.visibility = View.GONE
            }
            binding.itemScheduleFlip.setOnClickListener {
                binding.itemScheduleRv.visibility = View.GONE
                binding.itemScheduleFlip.visibility = View.GONE
                binding.itemScheduleMore.visibility = View.VISIBLE
                binding.itemScheduleDetail.visibility = View.VISIBLE
                val count = scheduleMap[category.categoryId]!!.count { it.status }
                val notyet = scheduleMap[category.categoryId]!!.size - count
                binding.itemScheduleDetail.text = count.toString() + " completed • "+notyet.toString() + " not yet"  //수정해야함
            }

        }
    }
}