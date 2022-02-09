package com.example.basehelpers.data.repository

import com.example.basehelpers.data.localdata.MainDao
import com.example.basehelpers.data.localdata.MainDatabase
import com.example.basehelpers.data.model.SampleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val mainDatabase: MainDatabase,
    private val mainDao: MainDao
) {


    suspend fun getSomeQuery(): SampleEntity {
        return withContext(Dispatchers.IO) {
            return@withContext mainDatabase.mainDao().someQuery()
        }
    }

    suspend fun insertSomeQuery(sampleEntity: SampleEntity) {
        withContext(Dispatchers.IO) {
            return@withContext mainDatabase.mainDao().insert(sampleEntity)
        }
    }
}