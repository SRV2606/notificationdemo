package com.example.basehelpers.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "sampleTable")
data class SampleEntity(
    @PrimaryKey
    val key: String = "1",
    var label: String = "hi I am inseted"
) : Parcelable
