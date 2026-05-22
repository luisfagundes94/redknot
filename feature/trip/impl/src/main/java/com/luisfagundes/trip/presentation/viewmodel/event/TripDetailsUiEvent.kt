package com.luisfagundes.trip.presentation.viewmodel.event

import com.luisfagundes.core.common.presentation.arch.event.UiEvent

internal sealed interface TripDetailsUiEvent : UiEvent {
    data class GetTripById(val id: Int) : TripDetailsUiEvent
    data class DeleteTrip(val id: Int) : TripDetailsUiEvent
    data class SelectTab(val index: Int) : TripDetailsUiEvent
}
