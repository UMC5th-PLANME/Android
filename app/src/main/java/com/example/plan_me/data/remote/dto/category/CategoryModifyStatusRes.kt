package com.example.plan_me.data.remote.dto.category

import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class ModifyStatusCategoryRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: modify_status_category_info
)

data class modify_status_category_info(
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("meStoryHidden") val meStoryHidden: Boolean,
    @SerializedName("updatedAt") val deletedAt: LocalTime,
)