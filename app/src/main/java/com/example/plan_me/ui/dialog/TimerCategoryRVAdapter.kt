package com.example.plan_me.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.databinding.ItemDrawerBinding
import com.example.plan_me.ui.main.MainDrawerRVAdapter

class TimerCategoryRVAdapter(private val context: Context, private val categoryList: List<CategoryList>, private val sendClickCategory: MainDrawerRVAdapter.SendClickCategory): RecyclerView.Adapter<TimerCategoryRVAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemDrawerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.itemDrawerTitleTv.text = categoryList[position].name
            binding.itemDrawerEmoticonTv.text = categoryList[position].emoticon

            binding.root.setOnClickListener {
                sendClickCategory.sendClickCategory(categoryList[position], position)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TimerCategoryRVAdapter.ViewHolder {
        val binding: ItemDrawerBinding = ItemDrawerBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimerCategoryRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = categoryList.size
}