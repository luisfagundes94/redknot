package com.luisfagundes.itinerary.domain.model

import java.time.LocalDate
import java.time.LocalTime

internal data class Activity(
    override val id: String,
    override val tripId: Int,
    override val date: LocalDate,
    override val time: LocalTime,
    val title: String,
    val description: String?,
    val location: String?,
    val imageUrl: String?
) : ItineraryItem