package com.luisfagundes.trip.data.model

import androidx.room.ColumnInfo
import com.luisfagundes.trip.data.database.converters.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
internal data class ItineraryDayEntity(
    @ColumnInfo("date") @Serializable(with = LocalDateSerializer::class) val date: LocalDate,
    @ColumnInfo("items") val items: List<ItineraryItemEntity>
)
