package com.example.plan_me.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plan_me.ui.timer.TimerFocusActivity

@Database(entities = [Time::class], version = 1)
abstract class TimeDatabase: RoomDatabase() {
    abstract fun timeDao(): TimeDao

    companion object {
        private var instance: TimeDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TimeDatabase? {
            if (instance == null) {
                synchronized(TimeDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TimeDatabase::class.java,
                        "time-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}