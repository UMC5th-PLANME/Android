
package com.example.plan_me.ui.planner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.databinding.ItemTodoBinding
import com.example.plan_me.data.remote.dto.schedule.ScheduleList

class PlannerRVAdapter(private val selectedSchedule : MutableList<ScheduleList>): RecyclerView.Adapter<PlannerRVAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemTodoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.todoTv.text = selectedSchedule[position].title
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): PlannerRVAdapter.ViewHolder {
        val binding: ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlannerRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
        if (selectedSchedule[position].status) {
            holder.binding.todoBtn.isChecked = true
        }
    }

    override fun getItemCount(): Int = selectedSchedule.size
}
