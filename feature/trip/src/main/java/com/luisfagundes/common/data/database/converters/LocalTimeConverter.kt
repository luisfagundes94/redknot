package com.luisfagundes.common.data.database.converters

import androidx.room.TypeConverter
import java.time.LocalTime

internal class LocalTimeConverter {
    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it) }
    }
}
