package com.luisfagundes.common.data.database.converters

import androidx.room.TypeConverter
import com.luisfagundes.trip.domain.model.TripStatus

internal class TripStatusConverter {
    @TypeConverter
    fun fromTripStatus(value: TripStatus): String {
        return value.name
    }

    @TypeConverter
    fun toTripStatus(value: String): TripStatus {
        return TripStatus.valueOf(value)
    }
}