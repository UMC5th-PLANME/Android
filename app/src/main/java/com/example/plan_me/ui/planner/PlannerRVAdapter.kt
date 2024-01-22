package com.example.plan_me.ui.planner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.databinding.ItemTodoBinding
import com.example.plan_me.entity.schedule
import java.time.LocalDate

class PlannerRVAdapter: RecyclerView.Adapter<PlannerRVAdapter.ViewHolder>() {
    val data1 = schedule(1, true, "toeic", LocalDate.of(2024, 1, 21))
    val data2 = schedule(1, false, "kotlin", LocalDate.of(2024, 1, 21))
    val data3 = schedule(1, false, "dart", LocalDate.of(2024, 1, 21))

    val dataList = arrayOf(data1, data2, data3)
    inner class ViewHolder(val binding: ItemTodoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.todoTv.text = dataList[position].content
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): PlannerRVAdapter.ViewHolder {
        val binding: ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlannerRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
        if (dataList[position].isDone) {
            holder.binding.todoBtn.isChecked = true
        }
    }

    override fun getItemCount(): Int = dataList.size
}