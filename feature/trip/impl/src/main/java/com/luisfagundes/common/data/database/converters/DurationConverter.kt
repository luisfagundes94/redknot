package com.luisfagundes.common.data.database.converters

import androidx.room.TypeConverter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

internal class DurationConverter {
    @TypeConverter
    fun fromDuration(duration: Duration): Long = duration.inWholeMilliseconds

    @TypeConverter
    fun toDuration(millis: Long): Duration = millis.milliseconds
}