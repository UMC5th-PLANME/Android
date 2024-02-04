package com.example.plan_me.data.local.entity

import androidx.room.Entity

@Entity(tableName = "EditProfile")
data class EditProfile(
    val name: String,
    val image_url: String
)
