package com.luisfagundes.trip.data.mapper

import com.luisfagundes.trip.data.model.TripEntity
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripStatus
import com.luisfagundes.trip.tools.extensions.convertMillisToLocalDate
import com.luisfagundes.trip.tools.extensions.toTimestampMillis

internal class TripMapper {
    fun mapToEntity(trip: Trip): TripEntity {
        return TripEntity(
            id = trip.id,
            startDate = trip.startDate.toTimestampMillis(),
            endDate = trip.endDate.toTimestampMillis(),
            imageUrl = trip.imageUrl,
            title = trip.title,
            location = trip.location
        )
    }

    fun mapToDomain(tripEntity: TripEntity): Trip {
        return Trip(
            id = tripEntity.id,
            startDate = tripEntity.startDate.convertMillisToLocalDate(),
            endDate = tripEntity.endDate.convertMillisToLocalDate(),
            imageUrl = tripEntity.imageUrl,
            title = tripEntity.title,
            location = tripEntity.location,
            status = TripStatus.UNSCHEDULED
        )
    }
}