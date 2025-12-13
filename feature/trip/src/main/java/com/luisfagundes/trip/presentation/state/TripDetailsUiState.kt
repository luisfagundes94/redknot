package com.luisfagundes.trip.presentation.state

import com.luisfagundes.trip.domain.model.Trip

internal sealed class TripDetailsUiState {
    data object Loading : TripDetailsUiState()
    data class Success(val trip: Trip) : TripDetailsUiState()
    data class Error(val message: String) : TripDetailsUiState()
}