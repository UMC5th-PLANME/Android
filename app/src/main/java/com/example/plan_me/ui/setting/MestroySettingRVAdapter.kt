package com.example.plan_me.ui.setting

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.category.ModifyStatusCategoryRes
import com.example.plan_me.data.remote.service.alarm.AlarmService
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.service.schedule.ScheduleService
import com.example.plan_me.data.remote.view.category.ModifyStatusCategoryView
import com.example.plan_me.databinding.ItemMestoryBinding
interface SendModifySuccess{
    fun sendModifySuccess()
}
class MestroySettingRVAdapter(private val categoryList : List<CategoryList>, private val context: Context, private val sendModifySuccess:SendModifySuccess): RecyclerView.Adapter<MestroySettingRVAdapter.ViewHolder>() {

    private lateinit var binding :ItemMestoryBinding

    inner class ViewHolder(val binding: ItemMestoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(category: CategoryList) {
            binding.itemMestoryEmoticonTv.text = category.emoticon
            binding.itemMestoryNameTv.text = category.name
            binding.itemMestoryHiddenBtn.isSelected = category.meStoryHidden
            binding.itemMestoryHiddenBtn.setOnClickListener {
                val access_token = "Bearer " + context.getSharedPreferences(
                    "getRes",
                    AppCompatActivity.MODE_PRIVATE
                ).getString("getAccessToken", "")
                val categoryService = CategoryService()
                categoryService.setModifyStatusCategoryView(object : ModifyStatusCategoryView {
                    override fun onModifyStatusCategorySuccess(response: ModifyStatusCategoryRes) {
                        binding.itemMestoryHiddenBtn.isSelected = response.result.meStoryHidden
                        sendModifySuccess.sendModifySuccess()
                    }

                    override fun onModifyStatusCategoryFailure(response: ModifyStatusCategoryRes) {
                        TODO("Not yet implemented")
                    }

                })
                categoryService.modifyStatusCategoryFun(access_token, category.categoryId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MestroySettingRVAdapter.ViewHolder {
        binding = ItemMestoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MestroySettingRVAdapter.ViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }


}