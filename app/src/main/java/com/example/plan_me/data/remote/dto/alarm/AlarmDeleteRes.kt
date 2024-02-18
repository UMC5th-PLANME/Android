package com.example.plan_me.data.remote.dto.alarm

import com.google.gson.annotations.SerializedName

data class AlarmDeleteRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: AlarmDelete
)

data class AlarmDelete(
    @SerializedName("schedule_id") val id: Int,
    @SerializedName("deleteAt") val deleteAt: String,
)