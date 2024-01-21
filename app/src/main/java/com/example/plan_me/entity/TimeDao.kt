package com.example.plan_me.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TimeDao {
    @Insert
    fun insert(time: Time)
    @Update
    fun update(time: Time)
    @Delete
    fun delete(time: Time)

    @Query("DELETE FROM TimeTable WHERE `set` = :set")
    fun deleteTimeBySet(set: Int): Int

    @Query("SELECT * FROM TimeTable")
    fun getTime() : List<Time>

    @Query("SELECT * FROM TimeTable WHERE `set` = :set")
    fun getSavedTime(set: Int): Time

    @Query("SELECT focusTime FROM TimeTable WHERE `set` = :set")
    fun getFocusTime(set: Long): Long

    @Query("SELECT breakTime FROM TimeTable WHERE `set` = :set")
    fun getBreakTime(set: Long): Long

    @Query("SELECT repeatCount FROM TimeTable WHERE `set` = :set")
    fun getRepeatCount(set: Int): Int

    @Query("UPDATE TimeTable SET focusTime = :focusTime, breakTime = :breakTime, repeatCount = :repeatCount WHERE `set` = :set")
    fun updateTime(focusTime: Long, breakTime: Long, repeatCount: Int, set: Int)

    @Query("UPDATE TimeTable SET focusTime = :focusTime WHERE `set` = :set")
    fun updateFocusTime(focusTime: Long, set: Int)

    @Query("UPDATE TimeTable SET breakTime = :breakTime WHERE `set` = :set")
    fun updateBreakTime(breakTime: Long, set: Int)

    @Query("UPDATE TimeTable SET repeatCount = :repeatCount WHERE `set` = :set")
    fun updateRepeatCount(repeatCount: Int, set: Int)
}