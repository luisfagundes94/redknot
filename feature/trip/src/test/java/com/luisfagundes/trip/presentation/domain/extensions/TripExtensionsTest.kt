package com.luisfagundes.trip.presentation.domain.extensions

import com.luisfagundes.trip.domain.extensions.withCalculatedStatus
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class TripExtensionsTest {

    @Test
    fun `withCalculatedStatus returns PAST when endDate is before today`() {
        val trip = createTrip(
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 1, 10)
        )
        val today = LocalDate.of(2024, 1, 15)

        val result = trip.withCalculatedStatus(today)

        assertEquals(TripStatus.PAST, result.status)
    }

    @Test
    fun `withCalculatedStatus returns UPCOMING when startDate is after today`() {
        val trip = createTrip(
            startDate = LocalDate.of(2024, 2, 1),
            endDate = LocalDate.of(2024, 2, 10)
        )
        val today = LocalDate.of(2024, 1, 15)

        val result = trip.withCalculatedStatus(today)

        assertEquals(TripStatus.UPCOMING, result.status)
    }

    @Test
    fun `withCalculatedStatus returns ONGOING when today is between start and end`() {
        val trip = createTrip(
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 1, 20)
        )
        val today = LocalDate.of(2024, 1, 15)

        val result = trip.withCalculatedStatus(today)

        assertEquals(TripStatus.ONGOING, result.status)
    }

    private fun createTrip(
        startDate: LocalDate,
        endDate: LocalDate
    ) = Trip(
        id = 1,
        startDate = startDate,
        endDate = endDate,
        imageUrl = "",
        title = "Test Trip",
        location = "Test Location",
        status = TripStatus.UPCOMING
    )
}