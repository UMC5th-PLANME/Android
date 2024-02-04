package com.example.plan_me.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Terms(
    val member_id: Int,
    val agreeTermIds: IntArray,
    val disagreeTermIds: IntArray
)
