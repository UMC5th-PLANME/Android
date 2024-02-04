package com.example.plan_me.data.remote.dto.schedule

import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class AddScheduleRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: schedule_info
)

