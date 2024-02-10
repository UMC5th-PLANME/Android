package com.example.plan_me.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.databinding.ItemDrawerBinding

class SelectCategoryRVAdapter(private val context: Context, private val categoryList : ArrayList<CategoryList>, dialogSelectCategoryInerface: DialogSelectCategoryInerface): RecyclerView.Adapter<SelectCategoryRVAdapter.ViewHolder>(){
    private var dialogSelectCategoryInerface: DialogSelectCategoryInerface

    init {
        this.dialogSelectCategoryInerface = dialogSelectCategoryInerface
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SelectCategoryRVAdapter.ViewHolder {
        val binding: ItemDrawerBinding = ItemDrawerBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: SelectCategoryRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = categoryList.size

    inner class ViewHolder(val binding: ItemDrawerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (position: Int) {
            val categoryText = categoryList[position].emoticon +" "+categoryList[position].name
            binding.itemDrawerTv.text = categoryText

            binding.root.setOnClickListener {
                dialogSelectCategoryInerface.onClickCategory(position)
            }
        }
    }


}
