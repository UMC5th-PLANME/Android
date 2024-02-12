package com.example.plan_me.data.remote.dto.mestory

import com.google.gson.annotations.SerializedName

data class GetMestoryTimeRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: GetMestoryResult
)

data class GetMestoryResult (
    @SerializedName("me_story_result") val me_story_result: List<GetTimeResult>
)

data class GetTimeResult (
    @SerializedName("category_id") val category_id : Int,
    @SerializedName("category_name") val category_name : String,
    @SerializedName("complete_num") val complete_num : Int,
    @SerializedName("in_progress_num") val in_progress_num : Int,
    @SerializedName("focus_time") val focus_time : String
)