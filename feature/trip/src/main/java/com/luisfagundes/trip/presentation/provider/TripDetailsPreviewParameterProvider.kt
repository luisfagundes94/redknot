package com.luisfagundes.trip.presentation.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripState
import com.luisfagundes.trip.presentation.state.TripDetailsUiState
import java.time.LocalDate
import java.time.Month

internal class TripDetailsPreviewParameterProvider : PreviewParameterProvider<TripDetailsUiState.Success> {
    private val trip =   Trip(
        id = 2,
        imageUrl = "https://images.pexels.com/photos/2422461/pexels-photo-2422461.jpeg",
        title = "Summer in Italy",
        startDate = LocalDate.of(2026, Month.AUGUST, 15),
        endDate = LocalDate.of(2026, Month.AUGUST, 25),
        location = "Florence, Italy",
        state = TripState.UPCOMING
    )

    override val values = sequenceOf(
        TripDetailsUiState.Success(
            trip = trip
        )
    )
}