package com.example.plan_me.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SettingTime::class], version = 1)
abstract class SettingTimeDatabase: RoomDatabase(){
    abstract fun SettingTimeDao(): SettingTimeDao

    companion object {
        private var instance: SettingTimeDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SettingTimeDatabase? {
            if (instance == null) {
                synchronized(SettingTimeDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SettingTimeDatabase::class.java,
                        "focus-time-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}