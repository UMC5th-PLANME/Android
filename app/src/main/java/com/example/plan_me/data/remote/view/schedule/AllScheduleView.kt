package com.example.plan_me.data.remote.view.schedule

import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import com.example.plan_me.data.remote.dto.schedule.AllScheduleRes
import com.example.plan_me.data.remote.dto.schedule.OneScheduleRes


interface AllScheduleView {
    fun onAllScheduleSuccess(response: AllScheduleRes)
    fun onAllScheduleFailure(response: AllScheduleRes)
}