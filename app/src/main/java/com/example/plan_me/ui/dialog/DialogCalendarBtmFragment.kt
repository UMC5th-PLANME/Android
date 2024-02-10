package com.example.plan_me.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.databinding.FragmentDialogBtmCalendarBinding
import com.example.plan_me.ui.all.Daily.DailyRVAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogCalendarBtmFragment(private val categoryList: List<CategoryList>, private val groupedSchedules : MutableMap<Int, MutableList<ScheduleList>>, private val context : Context):BottomSheetDialogFragment() {
    lateinit var binding : FragmentDialogBtmCalendarBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogBtmCalendarBinding.inflate(layoutInflater)
        Log.d("categoryList", categoryList.toString())
        Log.d("schedule", groupedSchedules.toString())
        initRV()
        return binding.root
    }

    private fun initRV() {
        val dailyRVAdapter = DialogCalendarBtmRVAdapter(categoryList, groupedSchedules, requireContext())
        binding.dialogCalenderBtmRv.adapter = dailyRVAdapter
        binding.dialogCalenderBtmRv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}