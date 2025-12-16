package com.luisfagundes.trip.presentation.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripStatus
import com.luisfagundes.trip.presentation.state.TripListUiState
import java.time.LocalDate
import java.time.Month

internal class TripListPreviewParameterProvider : PreviewParameterProvider<TripListUiState.Success> {
    private val trips = listOf(
        Trip(
            id = 3,
            imageUrl = "https://images.pexels.com/photos/2082103/pexels-photo-2082103.jpeg",
            title = "Weekend in Paris",
            startDate = LocalDate.of(2025, Month.DECEMBER, 10),
            endDate = LocalDate.of(2025, Month.DECEMBER, 24),
            location = "Paris, France",
            itinerary = null,
            status = TripStatus.ONGOING
        ),
        Trip(
            id = 2,
            imageUrl = "https://images.pexels.com/photos/2422461/pexels-photo-2422461.jpeg",
            title = "Summer in Italy",
            startDate = LocalDate.of(2026, Month.AUGUST, 15),
            endDate = LocalDate.of(2026, Month.AUGUST, 25),
            location = "Florence, Italy",
            itinerary = null,
            status = TripStatus.UPCOMING
        ),
        Trip(
            id = 1,
            imageUrl = "https://images.pexels.com/photos/3411135/pexels-photo-3411135.jpeg",
            title = "Disney Tour",
            startDate = LocalDate.of(2022, Month.APRIL, 2),
            endDate = LocalDate.of(2022, Month.APRIL, 24),
            location = "Orlando, United States",
            itinerary = null,
            status = TripStatus.PAST
        )
    )

    override val values = sequenceOf(
        TripListUiState.Success(
            tripsByStatus = trips.groupBy { it.status }
        )
    )
}