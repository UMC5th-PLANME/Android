package com.example.plan_me.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SettingTimeTable")
data class SettingTime(
    var baseFocusTime: Int = 3000000,
    var remainingFocusTime: Int = 3000000,
    var baseBreakTime: Int = 600000,
    var remainingBreakTime: Int = 600000
) {
    @PrimaryKey var set: Int? = null
}