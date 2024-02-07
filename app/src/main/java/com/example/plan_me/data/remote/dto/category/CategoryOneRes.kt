package com.example.plan_me.data.remote.dto.category

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime

data class OneCategoryRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: CategoryList
)