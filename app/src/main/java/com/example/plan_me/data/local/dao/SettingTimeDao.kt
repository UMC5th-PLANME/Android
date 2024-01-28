package com.example.plan_me.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.plan_me.data.local.entity.SettingTime

@Dao
interface SettingTimeDao {
    @Insert
    fun insert(time: SettingTime)
    @Update
    fun update(time: SettingTime)
    @Delete
    fun delete(time: SettingTime)

    @Query("SELECT * FROM SettingTimeTable")
    fun getTime() : List<SettingTime>

    @Query("SELECT * FROM SettingTimeTable WHERE `set` = :set")
    fun getSavedTime(set: Int): SettingTime

    @Query("SELECT BaseFocusTime FROM SettingTimeTable WHERE `set` = :set")
    fun getBaseFocusTime(set: Long): Long

    @Query("SELECT RemainingFocusTime FROM SettingTimeTable WHERE `set` = :set")
    fun getRemainingFocusTime(set: Long): Long

    @Query("SELECT BaseBreakTime FROM SettingTimeTable WHERE `set` = :set")
    fun getBaseBreakTime(set: Long): Long

    @Query("SELECT RemainingBreakTime FROM SettingTimeTable WHERE `set` = :set")
    fun getRemainingBreakTime(set: Long): Long
    @Query("UPDATE SettingTimeTable SET baseFocusTime = :baseFocusTime, remainingFocusTime = :remainingFocusTime, baseBreakTime = :baseBreakTime, remainingBreakTime = :remainingBreakTime WHERE `set` = :set")
    fun updateTime(baseFocusTime: Long, remainingFocusTime: Long, baseBreakTime: Long, remainingBreakTime: Long, set: Int)

    @Query("UPDATE SettingTimeTable SET remainingFocusTime = :remainingFocusTime WHERE `set` = :set")
    fun updateRemainingFocusTime(remainingFocusTime: Long, set: Int)

}