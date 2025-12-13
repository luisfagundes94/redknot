package com.luisfagundes.trip.data.database.converters

import androidx.room.TypeConverter
import com.luisfagundes.trip.domain.model.TripState

internal class TripStateConverter {
    @TypeConverter
    fun fromTripState(value: TripState): String {
        return value.name
    }

    @TypeConverter
    fun toTripState(value: String): TripState {
        return TripState.valueOf(value)
    }
}