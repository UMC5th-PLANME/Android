package com.example.plan_me.data.remote.view.schedule

import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import com.example.plan_me.data.remote.dto.schedule.OneScheduleRes


interface OneScheduleView {
    fun onOneScheduleSuccess(response: OneScheduleRes)
    fun onOneScheduleFailure(response: OneScheduleRes)
}