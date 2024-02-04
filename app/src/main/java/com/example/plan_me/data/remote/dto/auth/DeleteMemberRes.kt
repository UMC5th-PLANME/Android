package com.example.plan_me.data.remote.dto.auth

import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class DeleteMemberRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: DeleteMember
)

data class DeleteMember(
    @SerializedName("member_id") val member_id: Int,
    @SerializedName("deleted_at") val deleted_at: LocalTime
)
