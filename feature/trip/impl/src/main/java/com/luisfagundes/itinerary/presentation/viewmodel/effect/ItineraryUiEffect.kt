package com.luisfagundes.itinerary.presentation.viewmodel.effect

import com.luisfagundes.core.presentation.arch.effect.UiEffect

internal sealed interface ItineraryUiEffect : UiEffect {
    data object NavigateToItineraryItemForm : ItineraryUiEffect
}