package com.luisfagundes.trip.data.database.converters

import androidx.room.TypeConverter
import com.luisfagundes.trip.data.model.ItineraryItemEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class ItineraryItemListConverter {
    @TypeConverter
    fun fromItineraryItemList(value: List<ItineraryItemEntity>?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toItineraryItemList(value: String?): List<ItineraryItemEntity>? {
        return value?.let { Json.decodeFromString<List<ItineraryItemEntity>>(it) }
    }
}
