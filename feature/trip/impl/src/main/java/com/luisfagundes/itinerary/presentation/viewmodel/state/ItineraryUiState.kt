package com.luisfagundes.itinerary.presentation.viewmodel.state

import com.luisfagundes.core.common.presentation.arch.state.UiState
import com.luisfagundes.itinerary.domain.model.ItineraryItem
import java.time.LocalDate

internal sealed interface ItineraryUiState : UiState {
    data object Loading : ItineraryUiState
    data object Empty : ItineraryUiState
    data class Content(val itemsByDay: Map<LocalDate, List<ItineraryItem>>) : ItineraryUiState
}