package com.luisfagundes.itinerary.presentation

import com.luisfagundes.itinerary.domain.model.ItineraryItem

internal sealed interface ItineraryUiState {
    data object Loading : ItineraryUiState
    data object Empty : ItineraryUiState
    data class Content(val items: List<ItineraryItem>) : ItineraryUiState
}