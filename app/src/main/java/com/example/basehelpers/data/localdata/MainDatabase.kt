package com.example.basehelpers.data.localdata

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.basehelpers.data.model.SampleEntity

@Database(entities = [SampleEntity::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {

    abstract fun mainDao(): MainDao

}