package com.luisfagundes.trip.domain.model

import java.time.LocalTime
import kotlin.time.Duration

internal sealed class ItineraryItem {
    abstract val id: String
    abstract val time: LocalTime

    data class Flight(
        override val id: String,
        override val time: LocalTime,
        val flightNumber: String,
        val origin: Airport,
        val destination: Airport,
        val duration: Duration,
        val seatNumber: String
    ) : ItineraryItem()

    data class Accommodation(
        override val id: String,
        override val time: LocalTime,
        val name: String,
        val address: String,
        val checkInType: CheckInType,
        val imageUrl: String
    ) : ItineraryItem()

    data class Restaurant(
        override val id: String,
        override val time: LocalTime,
        val name: String,
    ) : ItineraryItem()

    data class Activity(
        override val id: String,
        override val time: LocalTime,
        val title: String,
        val description: String?,
        val location: String?,
        val imageUrl: String?
    ) : ItineraryItem()
}
