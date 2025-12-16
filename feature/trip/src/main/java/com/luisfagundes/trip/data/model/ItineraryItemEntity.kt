package com.luisfagundes.trip.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.luisfagundes.trip.data.database.converters.AirportConverter
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "itinerary_items",
    foreignKeys = [
        ForeignKey(
            entity = TripEntity::class,
            parentColumns = ["id"],
            childColumns = ["trip_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("trip_id"), Index("date")]
)
@TypeConverters(AirportConverter::class)
internal data class ItineraryItemEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: String,
    @ColumnInfo("trip_id") val tripId: Int,
    @ColumnInfo("date") val date: LocalDate,
    @ColumnInfo("time") val time: LocalTime,
    @ColumnInfo("type") val type: String,

    // Flight fields
    @ColumnInfo("flight_number") val flightNumber: String? = null,
    @ColumnInfo("origin_airport") val originAirport: AirportEntity? = null,
    @ColumnInfo("destination_airport") val destinationAirport: AirportEntity? = null,
    @ColumnInfo("duration_millis") val durationMillis: Long? = null,
    @ColumnInfo("seat_number") val seatNumber: String? = null,

    // Accommodation fields
    @ColumnInfo("accommodation_name") val accommodationName: String? = null,
    @ColumnInfo("address") val address: String? = null,
    @ColumnInfo("check_in_type") val checkInType: String? = null,
    @ColumnInfo("accommodation_image_url") val accommodationImageUrl: String? = null,

    // Restaurant fields
    @ColumnInfo("restaurant_name") val restaurantName: String? = null,

    // Activity fields
    @ColumnInfo("activity_title") val activityTitle: String? = null,
    @ColumnInfo("description") val description: String? = null,
    @ColumnInfo("location") val location: String? = null,
    @ColumnInfo("activity_image_url") val activityImageUrl: String? = null
)
