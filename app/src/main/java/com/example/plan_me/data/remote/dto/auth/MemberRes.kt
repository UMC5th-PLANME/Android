package com.example.plan_me.data.remote.dto.auth

import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class MemberRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: LookUpMember
)

data class LookUpMember(
    @SerializedName("member_id") val member_id: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profile_image") val profile_image: String,
    @SerializedName("login_type") val login_type: String,
    @SerializedName("email") val email: String,
    @SerializedName("created_at") val created_at: LocalTime,
    @SerializedName("updated_at") val updated_at: LocalTime,
    @SerializedName("status") val status: Int
)