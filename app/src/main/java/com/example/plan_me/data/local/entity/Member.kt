package com.example.plan_me.data.local.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Member")
data class Member(
    val name: String,
    val profile_image: String,
    val login_type: String,
    val email: String
)
