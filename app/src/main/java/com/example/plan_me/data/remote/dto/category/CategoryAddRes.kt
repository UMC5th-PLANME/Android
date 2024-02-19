package com.example.plan_me.data.remote.dto.category

import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class AddCategoryRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: add_category_info
)

data class add_category_info(
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("created_at") val created_at: String,
)