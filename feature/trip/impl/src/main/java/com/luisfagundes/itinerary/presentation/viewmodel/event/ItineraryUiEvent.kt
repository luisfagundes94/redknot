package com.luisfagundes.itinerary.presentation.viewmodel.event

import com.luisfagundes.core.common.presentation.arch.event.UiEvent
import com.luisfagundes.itinerary.domain.model.ItineraryItem

internal sealed interface ItineraryUiEvent : UiEvent {
    data class GetItineraryItems(val tripId: Int) : ItineraryUiEvent
    data object NavigateToAddItineraryItem : ItineraryUiEvent
    data class NavigateToEditItineraryItem(val item: ItineraryItem) : ItineraryUiEvent
}
