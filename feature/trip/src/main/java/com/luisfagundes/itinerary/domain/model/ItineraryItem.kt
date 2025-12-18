package com.luisfagundes.itinerary.domain.model

import java.time.LocalDate
import java.time.LocalTime

internal sealed class ItineraryItem {
    abstract val id: String
    abstract val tripId: Int
    abstract val date: LocalDate
    abstract val time: LocalTime
}
