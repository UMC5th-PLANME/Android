package com.example.plan_me.data.remote.dto.timer

import com.google.gson.annotations.SerializedName

data class GetTimerRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: TimeData
)

data class TimeData(
    @SerializedName("focusTime") val focusTime: String,
    @SerializedName("breakTime") val breakTime: String,
    @SerializedName("repeatCnt") val repeatCnt: Int,
    @SerializedName("currentRepeatCnt") val currentRepeatCnt: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
)