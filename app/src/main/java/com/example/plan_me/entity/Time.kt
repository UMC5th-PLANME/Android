package com.example.plan_me.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TimeTable")
data class Time (
    var focusTime : Int = 50,
    var breakTime : Int = 10,
    var repeatCount : Int = 1
) {
    @PrimaryKey(autoGenerate = true) var set: Int = 0
}
