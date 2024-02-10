package com.example.plan_me.data.remote.dto.schedule

import com.example.plan_me.data.remote.dto.category.CategoryList
import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class OneScheduleRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ScheduleList
)