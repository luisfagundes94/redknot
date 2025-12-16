package com.luisfagundes.trip.data.mapper

import com.luisfagundes.trip.data.model.ItineraryDayEntity
import com.luisfagundes.trip.domain.model.ItineraryDay

internal class ItineraryDayMapper(
    private val itineraryItemMapper: ItineraryItemMapper
) {
    fun mapToDomain(source: ItineraryDayEntity): ItineraryDay {
        return ItineraryDay(
            date = source.date,
            items = source.items.map { itemEntity -> itineraryItemMapper.mapToDomain(itemEntity) }
        )
    }

    fun mapToEntity(source: ItineraryDay): ItineraryDayEntity {
        return ItineraryDayEntity(
            date = source.date,
            items = source.items.map { item -> itineraryItemMapper.mapToEntity(item) }
        )
    }
}