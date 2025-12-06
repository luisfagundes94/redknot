package com.luisfagundes.trip.presentation.state

import com.luisfagundes.trip.domain.model.Trip
import com.luisfagundes.trip.domain.model.TripSection

internal sealed class TripListUiState {
    data object Loading : TripListUiState()
    data object Empty : TripListUiState()
    data object Error : TripListUiState()
    data class Content(val tripSectionList: List<TripSection>) : TripListUiState()
}