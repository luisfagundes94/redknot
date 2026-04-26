package com.luisfagundes.trip.domain.extensions

import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripStatus
import java.time.LocalDate

internal fun Trip.withCalculatedStatus(today: LocalDate = LocalDate.now()): Trip {
    val calculatedStatus = when {
        endDate.isBefore(today) -> TripStatus.PAST
        startDate.isAfter(today) -> TripStatus.UPCOMING
        else -> TripStatus.ONGOING
    }
    return copy(status = calculatedStatus)
}