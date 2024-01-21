package com.example.plan_me.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SettingTimeTable")
data class SettingTime(
    var baseFocusTime: Long = 3000000,
    var remainingFocusTime: Long = 3000000,
    var baseBreakTime: Long = 600000,
    var remainingBreakTime: Long = 600000
) {
    @PrimaryKey var set: Int? = null
}