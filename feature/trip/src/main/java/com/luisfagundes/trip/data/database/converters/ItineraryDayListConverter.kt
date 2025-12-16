package com.luisfagundes.trip.data.database.converters

import androidx.room.TypeConverter
import com.luisfagundes.trip.data.model.ItineraryDayEntity
import kotlinx.serialization.json.Json

internal class ItineraryDayListConverter {
    @TypeConverter
    fun fromItineraryDayList(value: List<ItineraryDayEntity>?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toItineraryDayList(value: String?): List<ItineraryDayEntity>? {
        return value?.let { Json.decodeFromString<List<ItineraryDayEntity>>(it) }
    }
}
