package com.luisfagundes.itinerary.presentation.viewmodel.effect

import com.luisfagundes.core.common.presentation.arch.effect.UiEffect

internal sealed class ActivityFormUiEffect : UiEffect {
    data object NavigateBack : ActivityFormUiEffect()
    data object NavigateBackToTripDetails : ActivityFormUiEffect()
    data class ShowErrorToast(val error: String) : ActivityFormUiEffect()
}
