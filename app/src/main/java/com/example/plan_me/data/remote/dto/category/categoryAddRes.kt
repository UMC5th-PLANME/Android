package com.example.plan_me.data.remote.dto.category

import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class AddCategoryRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: category_info
)

data class category_info(
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("created_at") val created_at: LocalTime,
)