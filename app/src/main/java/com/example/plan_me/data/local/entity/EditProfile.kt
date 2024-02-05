package com.example.plan_me.data.local.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("EditProfile")
data class EditProfile(
    val name: String,
    val image_url: String
)