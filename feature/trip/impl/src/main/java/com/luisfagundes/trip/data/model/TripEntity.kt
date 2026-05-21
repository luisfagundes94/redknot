package com.luisfagundes.trip.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "trips")
internal data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("start_date") val startDate: LocalDate,
    @ColumnInfo("end_date") val endDate: LocalDate,
    @ColumnInfo("image_url") val imageUrl: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("location") val location: String,
    @ColumnInfo("total_budget") val totalBudget: String? = null,
    @ColumnInfo("currency") val currency: String = "EUR"
)
