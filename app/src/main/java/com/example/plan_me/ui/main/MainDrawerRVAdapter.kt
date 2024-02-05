package com.example.plan_me.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.databinding.ItemDrawerBinding
import com.example.plan_me.databinding.ItemMestoryCategoryOpenBinding

class MainDrawerRVAdapter(private val categoryList : List<CategoryList>, private val sendClickCategory: SendClickCategory): RecyclerView.Adapter<MainDrawerRVAdapter.ViewHolder>(){

    interface SendClickCategory{
        fun sendClickCategory(category:CategoryList)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MainDrawerRVAdapter.ViewHolder {
        val binding: ItemDrawerBinding = ItemDrawerBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MainDrawerRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = categoryList.size

    inner class ViewHolder(val binding: ItemDrawerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (position: Int) {
            val categoryText = categoryList[position].emoticon +" "+categoryList[position].name
            binding.itemDrawerTv.text = categoryText

            binding.root.setOnClickListener {
                sendClickCategory.sendClickCategory(categoryList[position])
            }
        }
    }
}
