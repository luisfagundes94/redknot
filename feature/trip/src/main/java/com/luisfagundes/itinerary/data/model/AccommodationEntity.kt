package com.luisfagundes.itinerary.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.luisfagundes.trip.data.model.TripEntity
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "accommodations",
    foreignKeys = [
        ForeignKey(
            entity = TripEntity::class,
            parentColumns = ["id"],
            childColumns = ["trip_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("trip_id")]
)
internal data class AccommodationEntity(
    @PrimaryKey val id: String,
    @ColumnInfo("trip_id") val tripId: Int,
    @ColumnInfo("date") val date: LocalDate,
    @ColumnInfo("time") val time: LocalTime,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("address") val address: String,
    @ColumnInfo("check_in_type") val checkInType: String,
    @ColumnInfo("image_url") val imageUrl: String
)