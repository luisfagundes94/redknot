package com.luisfagundes.itinerary.domain.model

import java.time.LocalDate
import java.time.LocalTime

internal data class Accommodation(
    override val id: String,
    override val tripId: Int,
    override val date: LocalDate,
    override val time: LocalTime,
    val name: String,
    val address: String,
    val checkInType: CheckInType,
    val imageUrl: String
) : ItineraryItem()