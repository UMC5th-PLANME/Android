package com.example.plan_me.data.local.entity

import com.example.plan_me.data.remote.dto.schedule.ScheduleList
import java.time.LocalDate

data class Schedule_filter(
    var categoryId:Int,
    var scheduleList: List<ScheduleList>
)
