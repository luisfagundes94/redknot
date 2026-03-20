package com.luisfagundes.trip.presentation.viewmodel.state

import com.luisfagundes.core.presentation.arch.UiState
import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripStatus

internal sealed class TripListUiState : UiState {
    data object Loading : TripListUiState()
    data object Empty : TripListUiState()
    data object Error : TripListUiState()
    data class Success(val tripsByStatus: Map<TripStatus, List<Trip>>) : TripListUiState()
}