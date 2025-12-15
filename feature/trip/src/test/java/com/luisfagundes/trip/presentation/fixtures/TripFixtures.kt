package com.luisfagundes.trip.presentation.fixtures

import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripStatus
import java.time.LocalDate

internal val fakeUpcomingTrip = Trip(
    id = 1,
    title = "Summer In Italy",
    location = "Rome, Italy",
    startDate = LocalDate.of(2025,6, 15),
    endDate = LocalDate.of(2025,6, 25),
    imageUrl = "https://images.unsplash.com/photo-1506744038136-462",
    status = TripStatus.UPCOMING
)

internal val fakePastTrip = Trip(
    id = 2,
    title = "Weekend at Paris",
    location = "Paris, France",
    startDate = LocalDate.of(2024,8, 15),
    endDate = LocalDate.of(2024,8, 25),
    imageUrl = "https://images.unsplash.com/photo-1506744038136-462",
    status = TripStatus.PAST
)