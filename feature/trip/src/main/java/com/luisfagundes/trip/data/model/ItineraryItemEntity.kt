package com.luisfagundes.trip.data.model

import androidx.room.ColumnInfo
import com.luisfagundes.trip.data.database.converters.serializers.LocalTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalTime

@Serializable
internal data class ItineraryItemEntity(
    @ColumnInfo("id") val id: String,
    @ColumnInfo("type") val type: String,
    @ColumnInfo("time") @Serializable(with = LocalTimeSerializer::class) val time: LocalTime,

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
