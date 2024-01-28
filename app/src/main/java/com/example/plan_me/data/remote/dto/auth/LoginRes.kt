package com.example.plan_me.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class LoginRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
)
