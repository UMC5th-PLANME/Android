package com.example.plan_me.data.local.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Terms")
data class Terms(
    val member_id: Int,
    val agreeTermIds: List<Int>,
    val disagreeTermIds: List<Int>
)
