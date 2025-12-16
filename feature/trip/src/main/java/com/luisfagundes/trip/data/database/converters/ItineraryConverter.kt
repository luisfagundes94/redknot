package com.luisfagundes.trip.data.database.converters

import androidx.room.TypeConverter
import com.luisfagundes.trip.data.model.ItineraryEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class ItineraryConverter {
    @TypeConverter
    fun fromItinerary(value: ItineraryEntity?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toItinerary(value: String?): ItineraryEntity? {
        return value?.let { Json.decodeFromString<ItineraryEntity>(it) }
    }
}
