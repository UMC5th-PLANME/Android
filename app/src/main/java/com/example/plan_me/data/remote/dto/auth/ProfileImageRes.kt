package com.example.plan_me.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class ProfileImageRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ProfileImg
)

data class ProfileImg(
    @SerializedName("profile_image") val profile_image: String
)