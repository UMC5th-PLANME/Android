package com.example.plan_me.data.remote.dto.category

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime

data class AllCategoryRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: CategoryListResponse
)

data class CategoryListResponse(
    @SerializedName("categoryList") val categoryList: List<CategoryList>
)

data class CategoryList(
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("emoticon") val emoticon: String,
    @SerializedName("color") val color: Int,
    @SerializedName("meStoryHidden") val meStoryHidden: Boolean,
    @SerializedName("createdAt") val createdAt: String, // 날짜와 시간에 대한 포맷을 맞춰서 사용
    @SerializedName("updatedAt") val updatedAt: String // 날짜와 시간에 대한 포맷을 맞춰서 사용
)