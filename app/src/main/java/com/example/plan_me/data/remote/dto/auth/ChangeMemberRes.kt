package com.example.plan_me.data.remote.dto.auth

import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class ChangeMemberRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ChangeProfile
)

data class ChangeProfile(
    @SerializedName("member_id") val member_id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("profile_image") val profile_image: String,
    @SerializedName("updated_at") val updated_at: String
)