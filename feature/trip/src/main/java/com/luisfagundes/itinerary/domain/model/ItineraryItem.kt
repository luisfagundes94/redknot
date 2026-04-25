package com.luisfagundes.itinerary.domain.model

import java.time.LocalDate
import java.time.LocalTime

internal sealed interface ItineraryItem {
    val id: String
    val tripId: Int
    val date: LocalDate
    val time: LocalTime
}
