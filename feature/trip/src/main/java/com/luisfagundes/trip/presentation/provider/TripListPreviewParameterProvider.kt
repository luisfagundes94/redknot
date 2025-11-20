package com.luisfagundes.trip.presentation.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.presentation.state.TripListUiState

internal class TripListPreviewParameterProvider : PreviewParameterProvider<TripListUiState.Content> {
    private val trips = listOf(
        Trip(
            imageUrl = "https://images.pexels.com/photos/2422461/pexels-photo-2422461.jpeg",
            title = "Summer in Italy",
            period = "Aug 15 - Aug 25, 2026",
            location = "Florence, Italy"
        ),
        Trip(
            imageUrl = "https://images.pexels.com/photos/2082103/pexels-photo-2082103.jpeg",
            title = "Weekend in Paris",
            period = "Sep 20 - Sep 24, 2026",
            location = "Paris, France"
        )
    )

    override val values = sequenceOf(
        TripListUiState.Content(
            trips = trips
        )
    )
}