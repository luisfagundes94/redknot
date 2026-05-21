package com.luisfagundes.trip.data.mapper

import com.luisfagundes.trip.data.model.TripEntity
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripStatus
import java.math.BigDecimal
import javax.inject.Inject

internal class TripMapper @Inject constructor() {
    fun mapToEntity(trip: Trip): TripEntity {
        return TripEntity(
            id = trip.id,
            startDate = trip.startDate,
            endDate = trip.endDate,
            imageUrl = trip.imageUrl,
            title = trip.title,
            location = trip.location,
            totalBudget = trip.totalBudget?.toPlainString()
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
            status = TripStatus.UNSCHEDULED,
            totalBudget = tripEntity.totalBudget?.let { BigDecimal(it) }
        )
    }
}