package com.luisfagundes.trip.domain.model

private const val MAX_LIMIT = 999

internal enum class TripStatus(val displayOrder: Int) {
    ONGOING(0),
    UPCOMING(1),
    PAST(2),
    UNSCHEDULED(MAX_LIMIT)
}