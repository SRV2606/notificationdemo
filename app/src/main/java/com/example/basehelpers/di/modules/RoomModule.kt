package com.example.basehelpers.di.modules

import android.content.Context
import androidx.room.Room
import com.example.basehelpers.data.localdata.MainDao
import com.example.basehelpers.data.localdata.MainDatabase
import com.example.basehelpers.data.repository.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {


    @Provides
    fun provideRoomDatabse(@ApplicationContext context: Context): MainDatabase {
        return Room.databaseBuilder(context, MainDatabase::class.java, "main_database")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun mainDao(mainDatabase: MainDatabase): MainDao {
        return mainDatabase.mainDao()
    }

    @Provides
    fun provideRoomRepository(mainDatabase: MainDatabase, mainDao: MainDao): RoomRepository {
        return RoomRepository(mainDatabase, mainDao)
    }
}