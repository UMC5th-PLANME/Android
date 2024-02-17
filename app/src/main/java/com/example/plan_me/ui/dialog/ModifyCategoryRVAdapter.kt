package com.example.plan_me.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.databinding.ItemDrawerBinding

class ModifyCategoryRVAdapter(private val context: Context, private val categoryList : List<CategoryList>, private val sendModidyMessage :DialogModifyFragment.SendModifyMessage): RecyclerView.Adapter<ModifyCategoryRVAdapter.ViewHolder>(){
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ModifyCategoryRVAdapter.ViewHolder {
        val binding: ItemDrawerBinding = ItemDrawerBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ModifyCategoryRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = categoryList.size

    inner class ViewHolder(val binding: ItemDrawerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (position: Int) {

            binding.itemDrawerTitleTv.text = categoryList!![position].name
            binding.itemDrawerEmoticonTv.text = categoryList!![position].emoticon

            binding.root.setOnClickListener {
                showCheckDialog(position)
            }
        }
    }

    private fun showCheckDialog(position : Int) {
        val checkDialog = DialogModifyFragment(context, categoryList[position], sendModidyMessage, position)
        checkDialog.show()
    }
}
