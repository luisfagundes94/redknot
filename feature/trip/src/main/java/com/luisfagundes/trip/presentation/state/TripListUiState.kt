package com.luisfagundes.trip.presentation.state

import com.luisfagundes.trip.domain.model.Trip

internal sealed class TripListUiState {
    data object Loading : TripListUiState()
    data object Empty : TripListUiState()
    data class Content(
        val upcomingTrips: List<Trip>,
        val pastTrips: List<Trip>
    ) : TripListUiState()
}