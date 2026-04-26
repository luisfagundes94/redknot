package com.luisfagundes.itinerary.presentation.viewmodel.effect

import com.luisfagundes.core.common.presentation.arch.effect.UiEffect

internal sealed class AccommodationFormUiEffect : UiEffect {
    data object NavigateBack : AccommodationFormUiEffect()
    data object NavigateBackToTripDetails : AccommodationFormUiEffect()
    data class ShowErrorToast(val error: String) : AccommodationFormUiEffect()
}
