package com.example.plan_me.ui.planner

import android.content.Context
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.R
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.schedule.AllScheduleRes
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.data.remote.service.schedule.ScheduleService
import com.example.plan_me.data.remote.view.schedule.AllScheduleView
import com.example.plan_me.databinding.FragmentPlannerBinding
import com.example.plan_me.utils.viewModel.CalendarViewModel
import com.example.plan_me.utils.viewModel.CalendarViewModelFactory


class PlannerFragment : Fragment() ,
    PlannerRVAdapter.SendSignalModify{
    private lateinit var binding: FragmentPlannerBinding
    private lateinit var plannerRVAdapter : PlannerRVAdapter
    private var selectedSchedule : MutableList<ScheduleList>? = null
    val groupedSchedules = mutableMapOf<Int, MutableList<ScheduleList>>()


    private lateinit var calendarViewModel: CalendarViewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlannerBinding.inflate(layoutInflater)
        val factory = CalendarViewModelFactory(requireActivity().getSharedPreferences("getRes", Context.MODE_PRIVATE))
        calendarViewModel = ViewModelProvider(requireActivity(), factory).get(CalendarViewModel::class.java)

        binding.calendarViewModel = calendarViewModel
        binding.lifecycleOwner = this

        calendarViewModel._currentCategory.observe(viewLifecycleOwner, Observer {
            Log.d("_currentCategory",  calendarViewModel._currentCategory.value.toString())
            filteringSchedule()
            setSelectSchedule()
            setRvAdapter()
        })
        calendarViewModel._scheduleList.observe(viewLifecycleOwner, Observer {
            filteringSchedule()
            setSelectSchedule()
            setRvAdapter()
        })
        binding.plannerCategoryNameTv.isSelected = true

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        Log.d("Resume", "resume")
        calendarViewModel.getCategoryList()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun init() {
        Log.d("current",calendarViewModel._currentCategory.value!!.toString())
            val newColor = ContextCompat.getColor(requireContext(),  calendarViewModel._currentCategory.value!!.color) // Replace with your desired color resource
            val shape = GradientDrawable()
            shape.shape = GradientDrawable.RECTANGLE
            shape.setColor(newColor)
            shape.cornerRadius = resources.getDimension(R.dimen.planner_corner_raidus) // 원하는 radius 값으로 대체

            // 설정한 모양을 레이아웃에 적용
            binding.plannerSecondLo.background = shape
            binding.plannerCategoryNameTv.text = calendarViewModel._currentCategory.value!!.name
            binding.plannerCategoryImoticonTv.text = calendarViewModel._currentCategory.value!!.emoticon
    }

    private fun filteringSchedule() {
        Log.d("sche", calendarViewModel._scheduleList.value.toString())
        if (calendarViewModel._scheduleList.value != null) {
            // categoryId를 기준으로 ScheduleList를 그룹화하여 Schedule_filter 객체로 만듦
            groupedSchedules.clear()
            for (schedule in calendarViewModel._scheduleList.value!!) {
                val categoryId = schedule.category_id
                if (!groupedSchedules.containsKey(categoryId)) {
                    groupedSchedules[categoryId] = mutableListOf()
                }
                groupedSchedules[categoryId]?.add(schedule)
            }
        }
    }
    private fun setSelectSchedule() {
        Log.d("groupedSchedule11", groupedSchedules.toString())
        selectedSchedule = groupedSchedules[calendarViewModel._currentCategory.value!!.categoryId] ?: null
        Log.d("selected", selectedSchedule.toString())
    }

    private fun setRvAdapter() {
        if (!selectedSchedule.isNullOrEmpty()) {
            binding.plannerTodoRv.visibility = View.VISIBLE
            plannerRVAdapter = PlannerRVAdapter(calendarViewModel._categoryList.value!! ,selectedSchedule!!, requireContext(), this)
            binding.plannerTodoRv.layoutManager = LinearLayoutManager(requireContext())
            binding.plannerTodoRv.adapter = plannerRVAdapter
        }else {
            binding.plannerTodoRv.visibility = View.GONE
        }
    }
    override fun sendSignalModify() {
        calendarViewModel.getCategoryList()
    }
}