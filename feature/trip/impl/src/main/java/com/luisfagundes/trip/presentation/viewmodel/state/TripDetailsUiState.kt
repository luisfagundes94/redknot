package com.luisfagundes.trip.presentation.viewmodel.state

import com.luisfagundes.core.presentation.arch.state.UiState
import com.luisfagundes.trip.domain.model.Trip

internal sealed class TripDetailsUiState : UiState {
    data object Loading : TripDetailsUiState()
    data class Success(val trip: Trip) : TripDetailsUiState()
    data class Error(val message: String?) : TripDetailsUiState()
}