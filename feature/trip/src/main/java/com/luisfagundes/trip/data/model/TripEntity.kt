package com.luisfagundes.trip.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luisfagundes.trip.data.database.converters.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Entity
internal data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("start_date") @Serializable(with = LocalDateSerializer::class) val startDate: LocalDate,
    @ColumnInfo("end_date") @Serializable(with = LocalDateSerializer::class) val endDate: LocalDate,
    @ColumnInfo("image_url") val imageUrl: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("location") val location: String
)
