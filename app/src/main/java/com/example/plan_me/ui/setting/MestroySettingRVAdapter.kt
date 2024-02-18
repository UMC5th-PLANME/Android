package com.example.plan_me.ui.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.databinding.ItemMestoryBinding

class MestroySettingRVAdapter(private val categoryList : List<CategoryList>, private val context: Context): RecyclerView.Adapter<MestroySettingRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMestoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(category: CategoryList) {

            binding.itemMestoryEmoticonTv.text = category.emoticon
            binding.itemMestoryNameTv.text = category.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MestroySettingRVAdapter.ViewHolder {
        val binding: ItemMestoryBinding = ItemMestoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MestroySettingRVAdapter.ViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

}