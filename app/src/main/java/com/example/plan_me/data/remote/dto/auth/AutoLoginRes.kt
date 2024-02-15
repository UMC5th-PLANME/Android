package com.example.plan_me.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class AutoLoginRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: AutoLogin
)

data class AutoLogin(
    @SerializedName("member_id") val member_id: String,
    @SerializedName("expiration") val expiration: String
)