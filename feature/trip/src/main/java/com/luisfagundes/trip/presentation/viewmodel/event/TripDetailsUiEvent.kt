package com.luisfagundes.trip.presentation.viewmodel.event

import com.luisfagundes.core.presentation.arch.event.UiEvent

sealed interface TripDetailsUiEvent : UiEvent {
    data class OnGetTripById(val id: Int) : TripDetailsUiEvent
}