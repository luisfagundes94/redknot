package com.luisfagundes.itinerary.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.luisfagundes.itinerary.domain.model.ItineraryItemType
import com.luisfagundes.trip.data.model.TripEntity
import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.Duration

@Entity(
    tableName = "flights",
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
internal data class FlightEntity(
    @PrimaryKey override val id: String,
    @ColumnInfo("trip_id") override val tripId: Int,
    @ColumnInfo("date") override val date: LocalDate,
    @ColumnInfo("time") override val time: LocalTime,
    @ColumnInfo("flight_number") val flightNumber: String,
    @Embedded(prefix = "origin_") val origin: AirportEntity,
    @Embedded(prefix = "dest_") val destination: AirportEntity,
    @ColumnInfo("duration") val duration: Duration,
    @ColumnInfo("seat_number") val seatNumber: String
) : ItineraryItemEntity {
    override fun toItineraryItemType() = ItineraryItemType.FLIGHT
}