package com.luisfagundes.trip.domain.model

internal enum class TripStatus(val displayOrder: Int) {
    ONGOING(0),
    UPCOMING(1),
    PAST(2)
}