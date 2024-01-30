package com.example.plan_me.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Member")
data class Member(
    @PrimaryKey val email: String,
    val name: String,
    val profile_image: String,
    val login_type: String
)
