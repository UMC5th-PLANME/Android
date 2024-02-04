package com.example.plan_me.data.remote.dto.category

import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class DeleteCategoryRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: delete_category_info
)

data class delete_category_info(
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("deletedAt") val deletedAt: String,
)