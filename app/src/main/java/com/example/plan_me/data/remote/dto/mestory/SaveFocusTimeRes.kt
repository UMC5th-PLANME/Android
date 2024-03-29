package com.example.plan_me.data.remote.dto.mestory

import com.google.gson.annotations.SerializedName

data class TotalFocusTime(
    val totalFocusTime: String
)
data class SaveFocusTimeRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: FocusTimeSetting
)

data class FocusTimeSetting(
    @SerializedName("id") val id: Int,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("totalFocusTime") val totalFocusTime: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)
