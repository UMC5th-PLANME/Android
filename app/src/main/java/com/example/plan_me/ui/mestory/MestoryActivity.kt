package com.example.plan_me.ui.mestory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.databinding.ActivityMestoryBinding

class MestoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMestoryBinding

    val mestoryRVAdapter = MestoryRVAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMestoryBinding.inflate(layoutInflater)

        // RecyclerView 어댑터 설정
        binding.mestoryCategoryRv.layoutManager = LinearLayoutManager(this)

        // RecyclerView 레이아웃 매니저 설정
        binding.mestoryCategoryRv.adapter = mestoryRVAdapter

        setContentView(binding.root)
    }
}