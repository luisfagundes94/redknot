package com.luisfagundes.trip.presentation.viewmodel.event

import com.luisfagundes.core.presentation.arch.event.UiEvent

internal sealed interface TripListUiEvent : UiEvent {
    data object OnGetTripList : TripListUiEvent
    data object OnCreateTripClick : TripListUiEvent
    data object OnTryAgainClick : TripListUiEvent
    data class OnTripClick(val id: Int) : TripListUiEvent
}