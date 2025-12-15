package com.luisfagundes.trip.domain.model

import java.time.LocalDate

internal data class Trip(
    val id: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val imageUrl: String,
    val title: String,
    val location: String,
    val status: TripStatus
)

internal fun Trip.withCalculatedStatus(today: LocalDate = LocalDate.now()): Trip {
    val calculatedStatus = when {
        endDate.isBefore(today) -> TripStatus.PAST
        startDate.isAfter(today) -> TripStatus.UPCOMING
        else -> TripStatus.ONGOING
    }
    return copy(status = calculatedStatus)
}
