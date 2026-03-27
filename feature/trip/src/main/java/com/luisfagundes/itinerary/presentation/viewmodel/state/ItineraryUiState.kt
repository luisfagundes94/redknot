package com.luisfagundes.itinerary.presentation.viewmodel.state

import com.luisfagundes.core.presentation.arch.UiState
import com.luisfagundes.itinerary.domain.model.ItineraryItem

internal sealed interface ItineraryUiState : UiState {
    data object Loading : ItineraryUiState
    data object Empty : ItineraryUiState
    data class Content(val items: List<ItineraryItem>) : ItineraryUiState
}