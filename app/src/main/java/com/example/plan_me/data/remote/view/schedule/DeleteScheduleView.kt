package com.example.plan_me.data.remote.view.schedule

import com.example.plan_me.data.remote.dto.schedule.DeleteScheduleRes
import com.example.plan_me.data.remote.dto.schedule.OneScheduleRes


interface DeleteScheduleView {
    fun onDeleteScheduleSuccess(response: DeleteScheduleRes)
    fun onDeleteScheduleFailure(response: DeleteScheduleRes)
}