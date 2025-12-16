package com.luisfagundes.trip.data.model

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
internal data class ItineraryEntity(
    @ColumnInfo("days") val days: List<ItineraryDayEntity>
)
