package com.luisfagundes.trip.presentation.viewmodel.event

import com.luisfagundes.core.common.presentation.arch.event.UiEvent

internal sealed interface TripListUiEvent : UiEvent {
    data object GetTripList : TripListUiEvent
    data object NavigateToTripForm : TripListUiEvent
    data class NavigateToTripDetails(val id: Int) : TripListUiEvent
}
