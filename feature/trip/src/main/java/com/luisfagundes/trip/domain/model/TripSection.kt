package com.luisfagundes.trip.domain.model

internal data class TripSection(
    val type: TripSectionType,
    val trips: List<Trip>
)
