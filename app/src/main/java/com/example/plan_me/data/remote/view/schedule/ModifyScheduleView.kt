package com.example.plan_me.data.remote.view.schedule

import com.example.plan_me.data.remote.dto.category.AddCategoryRes
import com.example.plan_me.data.remote.dto.schedule.ModifyScheduleRes
import com.example.plan_me.data.remote.dto.schedule.OneScheduleRes


interface ModifyScheduleView {
    fun onModifyScheduleSuccess(response: ModifyScheduleRes)
    fun onModifyScheduleFailure(response: ModifyScheduleRes)
}