package com.luisfagundes.itinerary.presentation.viewmodel.event

import com.luisfagundes.core.presentation.arch.UiEvent

internal sealed interface ItineraryUiEvent : UiEvent {
    data object OnNewItineraryItemClick : ItineraryUiEvent
    data class OnGetItineraryList(val tripId: Int) : ItineraryUiEvent
}