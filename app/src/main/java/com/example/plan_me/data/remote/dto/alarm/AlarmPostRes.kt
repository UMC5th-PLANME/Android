package com.example.plan_me.data.remote.dto.alarm

import com.google.gson.annotations.SerializedName

data class AlarmPostRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: AlarmPost
)

data class AlarmPost(
    @SerializedName("id") val id: Int,
    @SerializedName("schedule_id") val schedule_id: Int,
)