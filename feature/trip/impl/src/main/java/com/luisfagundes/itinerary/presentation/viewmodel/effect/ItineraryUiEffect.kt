package com.luisfagundes.itinerary.presentation.viewmodel.effect

import com.luisfagundes.core.common.presentation.arch.effect.UiEffect
import com.luisfagundes.itinerary.domain.model.ItineraryItem

internal sealed interface ItineraryUiEffect : UiEffect {
    data object NavigateToItineraryItemForm : ItineraryUiEffect
    data class NavigateToEditItineraryItem(val item: ItineraryItem) : ItineraryUiEffect
}
