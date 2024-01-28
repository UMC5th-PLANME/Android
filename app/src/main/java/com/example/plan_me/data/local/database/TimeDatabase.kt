package com.example.plan_me.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plan_me.data.local.entity.Time
import com.example.plan_me.data.local.dao.TimeDao

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