package com.example.plan_me.ui.planner

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.R
import com.example.plan_me.data.remote.dto.schedule.AllScheduleRes
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.data.remote.service.schedule.ScheduleService
import com.example.plan_me.data.remote.view.schedule.AllScheduleView
import com.example.plan_me.databinding.FragmentPlannerBinding

class PlannerFragment : Fragment() ,
    AllScheduleView{
    private lateinit var binding: FragmentPlannerBinding
    private lateinit var plannerRVAdapter : PlannerRVAdapter
    private lateinit var schedules : List<ScheduleList>
    private var selectedSchedule : MutableList<ScheduleList>? = null
    val groupedSchedules = mutableMapOf<Int, MutableList<ScheduleList>>()
    private var categoryId : Int = 0

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlannerBinding.inflate(layoutInflater)
        init()
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        getScheduleAll()
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun init() {
        if (arguments != null) {
            categoryId = arguments?.getInt("id")!!
            val newColor = ContextCompat.getColor(requireContext(), arguments?.getInt("color")!!) // Replace with your desired color resource
            val shape = GradientDrawable()
            shape.shape = GradientDrawable.RECTANGLE
            shape.setColor(newColor)
            shape.cornerRadius = resources.getDimension(R.dimen.planner_corner_raidus) // 원하는 radius 값으로 대체

            // 설정한 모양을 레이아웃에 적용
            binding.plannerSecondLo.background = shape
            binding.plannerCategoryNameTv.text = (arguments?.getString("name")!!)
            binding.plannerCategoryImoticonTv.text = (arguments?.getString("emoticon")!!)

            getScheduleAll()
        }
    }

    private fun getScheduleAll() {
        val access_token = "Bearer " + requireActivity().getSharedPreferences("getRes",
            AppCompatActivity.MODE_PRIVATE
        ).getString("getAccessToken", "")
        val scheduleService = ScheduleService()
        scheduleService.setAllScheduleView(this)
        scheduleService.getScheduleAllFun(access_token)
    }

    private fun filteringSchedule() {
        // categoryId를 기준으로 ScheduleList를 그룹화하여 Schedule_filter 객체로 만듦
        for (schedule in schedules) {
            val categoryId = schedule.category_id
            if (!groupedSchedules.containsKey(categoryId)) {
                groupedSchedules[categoryId] = mutableListOf()
            }
            groupedSchedules[categoryId]?.add(schedule)
        }
        Log.d("groupedSchedule", groupedSchedules.toString())
    }
    private fun setSelectSchedule() {
        selectedSchedule = groupedSchedules[categoryId] ?: null
        Log.d("selected", selectedSchedule.toString())
    }

    private fun setRvAdapter() {
        if (!selectedSchedule.isNullOrEmpty()) {
            plannerRVAdapter = PlannerRVAdapter(selectedSchedule!!)
            binding.plannerTodoRv.layoutManager = LinearLayoutManager(requireContext())
            binding.plannerTodoRv.adapter = plannerRVAdapter
        }
    }

    override fun onAllScheduleSuccess(response: AllScheduleRes) {
        schedules = response.result.scheduleList
        filteringSchedule()
        setSelectSchedule()
        setRvAdapter()
    }

    override fun onAllScheduleFailure(response: AllScheduleRes) {
        TODO("Not yet implemented")
    }
}