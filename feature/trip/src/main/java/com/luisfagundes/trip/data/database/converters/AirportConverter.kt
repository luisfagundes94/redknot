package com.luisfagundes.trip.data.database.converters

import androidx.room.TypeConverter
import com.luisfagundes.trip.data.model.AirportEntity
import kotlinx.serialization.json.Json

internal class AirportConverter {
    @TypeConverter
    fun fromAirport(value: AirportEntity?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toAirport(value: String?): AirportEntity? {
        return value?.let { Json.decodeFromString<AirportEntity>(it) }
    }
}
