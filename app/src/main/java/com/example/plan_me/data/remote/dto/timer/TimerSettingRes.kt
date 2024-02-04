package com.example.plan_me.data.remote.dto.timer

import com.google.gson.annotations.SerializedName

data class TimerSettingReq(
    @SerializedName("focusTime") val focusTime: Int,
    @SerializedName("breakTime") val breakTime: Int,
    @SerializedName("repeatCnt") val repeatCnt: Int,
)
data class TimerSettingRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: TimerSetting
)

data class TimerSetting(
    @SerializedName("id") val id: Int,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)
