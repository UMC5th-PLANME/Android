package com.example.plan_me.entity

import androidx.room.Entity

@Entity(tableName = "TimeTable")
data class Time (
    val focusTime : Int = 50,
    val breakTime : Int = 10,
    val repeatCount : Int = 1
)
