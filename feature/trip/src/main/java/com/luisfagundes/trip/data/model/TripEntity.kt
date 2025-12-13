package com.luisfagundes.trip.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("start_date") val startDate: Long,
    @ColumnInfo("end_date") val endDate: Long,
    @ColumnInfo("image_url") val imageUrl: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("location") val location: String,
)
