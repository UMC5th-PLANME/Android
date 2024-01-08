package com.example.plan_me.ui.mestory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plan_me.databinding.ItemMestoryCategoryOpenBinding

class MestoryRVAdapter(): RecyclerView.Adapter<MestoryRVAdapter.ViewHolder>(){

    // test : category layout 확인
    val likeData = arrayOf( "Exercise", "Study" )

    // onCreateViewHolder : ViewHolder 를 생성해줘야 할 때 호출
    // 중요!! itemview 객체를 만든 뒤에 이를 재활용하기 위해 ViewHoler 에 던져줌
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MestoryRVAdapter.ViewHolder {
        val binding: ItemMestoryCategoryOpenBinding = ItemMestoryCategoryOpenBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    // onBindViewHolder : ViewHolder 에 데이터를 바인딩 해줘야 할 때마다 호출되는 함수
    // 사용자가 화면을 스크롤 할 때마다 엄청나게 호출됨
    // RecyclerView 안에서는 Index 를 position 이라고 함
    override fun onBindViewHolder(holder: MestoryRVAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = likeData.size

    inner class ViewHolder(val binding: ItemMestoryCategoryOpenBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (position: Int) {
            binding.mestoryCategoryTv.text = likeData[position]
        }
    }
}