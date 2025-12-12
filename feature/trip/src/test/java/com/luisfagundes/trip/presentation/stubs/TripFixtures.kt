package com.luisfagundes.trip.presentation.stubs

import com.luisfagundes.trip.domain.model.Trip
import java.time.LocalDate

internal val fakeTrip = Trip(
    id = 0,
    title = "Summer In Italy",
    location = "Rome, Italy",
    startDate = LocalDate.of(2025,6, 15),
    endDate = LocalDate.of(2025,6, 25),
    imageUrl = "https://images.unsplash.com/photo-1506744038136-462",
    done = false
)