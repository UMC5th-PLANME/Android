package com.example.plan_me.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class SignUpRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: MemberId
)

data class MemberId(
    @SerializedName("member_id") val member_id: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)
