package com.luisfagundes.trip.domain.model

import java.time.LocalDate

internal data class ItineraryDay(
    val date: LocalDate,
    val items: List<ItineraryItem>
)
