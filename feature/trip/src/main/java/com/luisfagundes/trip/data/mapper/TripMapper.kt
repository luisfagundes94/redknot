package com.luisfagundes.trip.data.mapper

import com.luisfagundes.trip.data.model.TripEntity
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripStatus
import javax.inject.Inject

internal class TripMapper @Inject constructor(
    private val itineraryMapper: ItineraryMapper
) {
    fun mapToEntity(trip: Trip): TripEntity {
        return TripEntity(
            id = trip.id,
            startDate = trip.startDate,
            endDate = trip.endDate,
            imageUrl = trip.imageUrl,
            title = trip.title,
            location = trip.location,
            itinerary = itineraryMapper.mapToEntity(trip.itinerary)
        )
    }

    fun mapToDomain(tripEntity: TripEntity): Trip {
        return Trip(
            id = tripEntity.id,
            startDate = tripEntity.startDate,
            endDate = tripEntity.endDate,
            imageUrl = tripEntity.imageUrl,
            title = tripEntity.title,
            location = tripEntity.location,
            itinerary = itineraryMapper.mapToDomain(tripEntity.itinerary),
            status = TripStatus.UNSCHEDULED
        )
    }
}