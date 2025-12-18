package com.luisfagundes.itinerary.data.model

import com.luisfagundes.itinerary.domain.model.ItineraryItemType
import java.time.LocalDate
import java.time.LocalTime

internal sealed interface ItineraryItemEntity {
    val id: String
    val tripId: Int
    val date: LocalDate
    val time: LocalTime

    fun toItineraryItemType(): ItineraryItemType
}