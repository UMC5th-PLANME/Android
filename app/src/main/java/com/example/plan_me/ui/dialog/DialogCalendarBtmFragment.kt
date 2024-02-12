package com.example.plan_me.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.category.CategoryList
import com.example.plan_me.data.remote.dto.schedule.AllScheduleRes
import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import com.example.plan_me.data.remote.service.category.CategoryService
import com.example.plan_me.data.remote.service.schedule.ScheduleService
import com.example.plan_me.data.remote.view.category.AllCategoryView
import com.example.plan_me.data.remote.view.schedule.AllScheduleView
import com.example.plan_me.databinding.FragmentDialogBtmCalendarBinding
import com.example.plan_me.ui.all.Daily.DailyRVAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate

class DialogCalendarBtmFragment(private val date : LocalDate, private val context : Context):BottomSheetDialogFragment(),
    AllCategoryView,
    AllScheduleView{
    lateinit var binding : FragmentDialogBtmCalendarBinding
    private lateinit var categoryList : List<CategoryList>
    private lateinit var scheduleList : List<ScheduleList>
    val groupedSchedules = mutableMapOf<Int, MutableList<ScheduleList>>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogBtmCalendarBinding.inflate(layoutInflater)
        getCategoryList()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getCategoryList()
    }

    private fun initRV() {
        filteringSchedule(date)
        val categoryList = filteringCategory()
        val dailyRVAdapter = DialogCalendarBtmRVAdapter(categoryList, groupedSchedules, requireContext())
        binding.dialogCalenderBtmRv.adapter = dailyRVAdapter
        binding.dialogCalenderBtmRv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun filteringSchedule(currentDate: LocalDate) {
        groupedSchedules.clear()
        for (schedule in scheduleList) {
            val categoryId = schedule.category_id
            val startDate = LocalDate.parse(schedule.startDate)
            val endDate = LocalDate.parse(schedule.endDate)

            if (currentDate.isEqual(startDate) || currentDate.isEqual(endDate) ||
                (currentDate.isAfter(startDate) && currentDate.isBefore(endDate))) {
                if (!groupedSchedules.containsKey(categoryId)) {
                    groupedSchedules[categoryId] = mutableListOf()
                }
                groupedSchedules[categoryId]?.add(schedule)
            }
        }
        Log.d(currentDate.toString(), groupedSchedules.toString())
    }

    private fun filteringCategory(): List<CategoryList> {
        val filteringCategory = mutableListOf<CategoryList>()

        for ((categoryId, _) in groupedSchedules) {
            val category = categoryList.find { it.categoryId == categoryId }
            category?.let {
                filteringCategory.add(it)
            }
        }

        return filteringCategory
    }

    private fun getScheduleAll() {
        val access_token = "Bearer " + requireActivity().getSharedPreferences("getRes",
            AppCompatActivity.MODE_PRIVATE
        ).getString("getAccessToken", "")
        val scheduleService = ScheduleService()
        scheduleService.setAllScheduleView(this)
        scheduleService.getScheduleAllFun(access_token)
    }

    private fun getCategoryList() {
        val access_token = "Bearer " + requireActivity().getSharedPreferences("getRes",
            AppCompatActivity.MODE_PRIVATE
        ).getString("getAccessToken", "")
        val setCategoryService = CategoryService()
        setCategoryService.setAllCategoryView(this)
        setCategoryService.getCategoryAllFun(access_token!!)
    }

    override fun onAllCategorySuccess(response: AllCategoryRes) {
        categoryList = response.result.categoryList
        getScheduleAll()
    }

    override fun onAllCategoryFailure(response: AllCategoryRes) {
        TODO("Not yet implemented")
    }

    override fun onAllScheduleSuccess(response: AllScheduleRes) {
        scheduleList = response.result.scheduleList
        Log.d("scheduleList", scheduleList.toString())
        initRV()
    }

    override fun onAllScheduleFailure(response: AllScheduleRes) {
        TODO("Not yet implemented")
    }
}