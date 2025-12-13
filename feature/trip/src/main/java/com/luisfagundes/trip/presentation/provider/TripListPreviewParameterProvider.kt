package com.luisfagundes.trip.presentation.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripState
import com.luisfagundes.trip.presentation.state.TripListUiState
import java.time.LocalDate
import java.time.Month

internal class TripListPreviewParameterProvider : PreviewParameterProvider<TripListUiState.Success> {
    private val upcomingTrips = listOf(
        Trip(
            id = 2,
            imageUrl = "https://images.pexels.com/photos/2422461/pexels-photo-2422461.jpeg",
            title = "Summer in Italy",
            startDate = LocalDate.of(2026, Month.AUGUST, 15),
            endDate = LocalDate.of(2026, Month.AUGUST, 25),
            location = "Florence, Italy",
            state = TripState.UPCOMING
        ),
        Trip(
            id = 3,
            imageUrl = "https://images.pexels.com/photos/2082103/pexels-photo-2082103.jpeg",
            title = "Weekend in Paris",
            startDate = LocalDate.of(2026, Month.SEPTEMBER, 20),
            endDate = LocalDate.of(2026, Month.SEPTEMBER, 24),
            location = "Paris, France",
            state = TripState.UPCOMING
        )
    )

    private val pastTrips = listOf(
        Trip(
            id = 1,
            imageUrl = "https://images.pexels.com/photos/3411135/pexels-photo-3411135.jpeg",
            title = "Disney Tour",
            startDate = LocalDate.of(2022, Month.APRIL, 2),
            endDate = LocalDate.of(2022, Month.APRIL, 24),
            location = "Orlando, United States",
            state = TripState.PAST
        )
    )

    override val values = sequenceOf(
        TripListUiState.Success(
            upcomingTrips = upcomingTrips,
            pastTrips = pastTrips
        )
    )
}