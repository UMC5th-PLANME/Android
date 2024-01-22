package com.example.plan_me.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TimeTable")
data class Time(
    var focusTime: Long = 3000000,
    var breakTime: Long = 600000,
    var repeatCount: Int = 1
) {
    @PrimaryKey var set: Int? = null
}
