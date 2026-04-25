package com.luisfagundes.itinerary.domain.model

import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.Duration

internal data class Flight(
    override val id: String,
    override val tripId: Int,
    override val date: LocalDate,
    override val time: LocalTime,
    val flightNumber: String,
    val origin: Airport,
    val destination: Airport,
    val duration: Duration,
    val seatNumber: String
) : ItineraryItem