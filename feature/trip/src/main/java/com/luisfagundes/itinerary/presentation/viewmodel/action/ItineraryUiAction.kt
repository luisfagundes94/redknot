package com.luisfagundes.itinerary.presentation.viewmodel.action

import com.luisfagundes.core.presentation.arch.UiAction

internal sealed interface ItineraryUiAction : UiAction {
    data object NavigateToItineraryItemForm : ItineraryUiAction
}