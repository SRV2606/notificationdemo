package com.example.basehelpers.data.localdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.basehelpers.data.model.SampleEntity

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sampleEntity: SampleEntity)

    @Query("SELECT * from sampleTable")
    fun someQuery(): SampleEntity

}
