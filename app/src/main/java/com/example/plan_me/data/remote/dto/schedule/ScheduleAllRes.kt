package com.example.plan_me.data.remote.dto.schedule

import com.example.plan_me.data.remote.dto.category.CategoryList
import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class AllScheduleRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ScheduleListResponse
)

data class ScheduleListResponse(
    @SerializedName("scheduleList") val scheduleList: List<schedule_info>
)

data class schedule_info(
    @SerializedName("id") val id: Int,
    @SerializedName("status") val status: Boolean,
    @SerializedName("category_id") val category_id: Int,
    @SerializedName("repeat_period") val repeat_period: String,
    @SerializedName("title") val title: String,
    @SerializedName("start_time") val start_time: String,
    @SerializedName("end_time") val end_time: String,
    @SerializedName("alarm") val alarm: Boolean,
    @SerializedName("alarm_time") val alarm_time: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
)