package com.example.plan_me.ui.all.Daily

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.R
import com.example.plan_me.databinding.ItemScheduleBinding
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.databinding.ItemScheduleListBinding
import com.example.plan_me.databinding.ItemTodoBinding

class DailyScheduleRVAdapter(private val scheduleMap: MutableList<ScheduleList>?) : RecyclerView.Adapter<DailyScheduleRVAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemScheduleListBinding = ItemScheduleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(scheduleMap!![position])
    }

    override fun getItemCount(): Int =
        if (scheduleMap.isNullOrEmpty()) 0
        else scheduleMap.size

    inner class ViewHolder(val binding: ItemScheduleListBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: ScheduleList) {
            binding.scheduleListTv.text = schedule.title
            binding.scheduleListBtn.isChecked = schedule.status
        }
    }
}