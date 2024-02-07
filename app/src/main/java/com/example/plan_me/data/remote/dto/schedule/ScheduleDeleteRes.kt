package com.example.plan_me.data.remote.dto.schedule

import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class DeleteScheduleRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: delete_schedule_info
)

data class delete_schedule_info(
    @SerializedName("member_id") val member_id: Int,
    @SerializedName("deleted_at") val deleted_at: LocalTime,
)