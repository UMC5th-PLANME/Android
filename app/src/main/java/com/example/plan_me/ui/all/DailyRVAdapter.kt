package com.example.plan_me.ui.all

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.databinding.ItemScheduleBinding
import com.example.plan_me.entity.category
import com.example.plan_me.entity.schedule

class DailyRVAdapter(private val categoryList : ArrayList<category>, private val scheduleList : ArrayList<schedule>) : RecyclerView.Adapter<DailyRVAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyRVAdapter.ViewHolder {
        val binding : ItemScheduleBinding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyRVAdapter.ViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int = categoryList.size
    inner class ViewHolder(val binding: ItemScheduleBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(category: category) {
            binding.itemScheduleTv.text = category.title
            binding.itemScheduleDetail.text = "!!!"

            binding.itemScheduleFlip.setOnClickListener {
                binding.itemScheduleRv.visibility = View.VISIBLE
                binding.itemScheduleMore.visibility = View.VISIBLE
                binding.itemScheduleFlip.visibility = View.GONE
                binding.itemScheduleDetail.visibility = View.GONE
            }
                binding.itemScheduleMore.setOnClickListener {
                    binding.itemScheduleRv.visibility = View.GONE
                    binding.itemScheduleMore.visibility = View.GONE
                    binding.itemScheduleFlip.visibility = View.VISIBLE
                    binding.itemScheduleDetail.visibility = View.VISIBLE
                }
        }
    }
}