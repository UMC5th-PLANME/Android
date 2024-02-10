package com.example.plan_me.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plan_me.data.local.entity.SettingTime
import com.example.plan_me.data.local.dao.SettingTimeDao

@Database(entities = [SettingTime::class], version = 1)
abstract class SettingDatabase: RoomDatabase(){
    abstract fun SettingTimeDao(): SettingTimeDao

    companion object {
        private var instance: SettingDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SettingDatabase? {
            if (instance == null) {
                synchronized(SettingDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SettingDatabase::class.java,
                        "focus-time-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}