package com.luisfagundes.trip.presentation.state

import com.luisfagundes.trip.domain.model.Trip

internal sealed class TripListUiState {
    data object Loading : TripListUiState()
    data object Empty : TripListUiState()
    data object Error : TripListUiState()
    data class Success(
        val upcomingTrips: List<Trip>,
        val pastTrips: List<Trip>
    ) : TripListUiState()
}