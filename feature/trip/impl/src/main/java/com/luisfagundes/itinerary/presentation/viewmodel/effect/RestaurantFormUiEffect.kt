package com.luisfagundes.itinerary.presentation.viewmodel.effect

import com.luisfagundes.core.presentation.arch.effect.UiEffect

internal sealed class RestaurantFormUiEffect : UiEffect {
    data object NavigateBack : RestaurantFormUiEffect()
    data object NavigateBackToTripDetails : RestaurantFormUiEffect()
    data class ShowErrorToast(val error: String) : RestaurantFormUiEffect()
}
