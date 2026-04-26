package com.luisfagundes.itinerary.presentation.viewmodel.effect

import com.luisfagundes.core.presentation.arch.effect.UiEffect

internal sealed class FlightFormUiEffect : UiEffect {
    data object NavigateToTripDetails : FlightFormUiEffect()
    data class ShowErrorToast(val error: String) : FlightFormUiEffect()
}
