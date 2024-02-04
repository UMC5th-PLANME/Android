package com.example.plan_me.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class TermsRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: AgreeTerm
)

data class AgreeTerm(
    @SerializedName("member_id") val member_id: Int,
    @SerializedName("agreeTermIds") val agreeTermIds: List<Int>,
    @SerializedName("disagreeTermIds") val disagreeTermIds: List<Int>
)
