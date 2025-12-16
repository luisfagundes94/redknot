package com.luisfagundes.trip.data.mapper

import com.luisfagundes.trip.data.model.ItineraryEntity
import com.luisfagundes.trip.domain.model.Itinerary
import javax.inject.Inject

internal class ItineraryMapper @Inject constructor(
    private val itineraryDayMapper: ItineraryDayMapper
) {
    fun mapToDomain(source: ItineraryEntity?): Itinerary? {
        return source?.let {
            Itinerary(
                days = it.days.map { dayEntity -> itineraryDayMapper.mapToDomain(dayEntity) }
            )
        }
    }

    fun mapToEntity(source: Itinerary?): ItineraryEntity? {
        return source?.let {
            ItineraryEntity(
                days = it.days.map { day -> itineraryDayMapper.mapToEntity(day) }
            )
        }
    }
}