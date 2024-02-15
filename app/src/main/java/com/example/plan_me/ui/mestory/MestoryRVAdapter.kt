package com.example.plan_me.ui.mestory

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.mestory.GetTimeResult
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.view.category.AllCategoryView
import com.example.plan_me.databinding.ItemMestoryCategoryOpenBinding

class MestoryRVAdapter(val context: Context, val result: List<GetTimeResult>): RecyclerView.Adapter<MestoryRVAdapter.ViewHolder>(),
    AllCategoryView {
    private var accessToken: String? = ""
    private var categoryId: Int = 0
    private var color: String? = ""

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MestoryRVAdapter.ViewHolder {
        val binding: ItemMestoryCategoryOpenBinding = ItemMestoryCategoryOpenBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        //mestoryColor()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MestoryRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = result.size

    inner class ViewHolder(val binding: ItemMestoryCategoryOpenBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (position: Int) {
            binding.mestoryCategoryTv.text = result[position].category_name
            //categoryData()
        }
    }

//    private fun getRemoteData() {
//        val sharedPreferences: SharedPreferences = context.getSharedPreferences("getRes", MODE_PRIVATE)
//        accessToken = sharedPreferences.getString("getAccessToken", accessToken)
//    }
//
//    private fun categoryData() {
//        val sharedPreferences: SharedPreferences = context.getSharedPreferences("category", MODE_PRIVATE)
//        categoryId = sharedPreferences.getInt("categoryId", categoryId)
//        color = sharedPreferences.getString("color", color)
//    }

//    private fun mestoryColor() {
//        getRemoteData()
//        categoryData()
//        val categoryOneService = CategoryService()
//        categoryOneService.setOneCategoryView(this@MestoryRVAdapter)
//        categoryOneService.getOneCategoryFun("Bearer " + accessToken, categoryId)
//    }

    override fun onAllCategorySuccess(response: AllCategoryRes) {
        Log.d("RESPONSE", response.result.toString())
    }

    override fun onAllCategoryFailure(response: AllCategoryRes) {
        Log.d("FAILURE", response.message)
    }
}
