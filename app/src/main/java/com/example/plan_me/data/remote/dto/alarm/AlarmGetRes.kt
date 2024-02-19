package com.example.plan_me.data.remote.dto.alarm

import com.example.plan_me.data.remote.dto.category.CategoryList
import com.google.gson.annotations.SerializedName

data class AlarmGetRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<AlarmGet>
)

data class AlarmGet(
    @SerializedName("id") val id: Int,
    @SerializedName("schedule_id") val schedule_id: Int
)