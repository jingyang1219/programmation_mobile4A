package com.example.houyuapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.houyuapp.data.local.models.UserLocal

@Database(entities = arrayOf(UserLocal::class), version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao
}