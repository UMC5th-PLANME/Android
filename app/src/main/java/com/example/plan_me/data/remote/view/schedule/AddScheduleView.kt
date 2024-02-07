package com.example.plan_me.data.remote.view.schedule

import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import com.example.plan_me.data.remote.dto.category.AllCategoryRes
import com.example.plan_me.data.remote.dto.schedule.AddScheduleRes


interface AddScheduleView {
    fun onAddScheduleSuccess(response: AddScheduleRes)
    fun onAddScheduleFailure(response: AddScheduleRes)
}