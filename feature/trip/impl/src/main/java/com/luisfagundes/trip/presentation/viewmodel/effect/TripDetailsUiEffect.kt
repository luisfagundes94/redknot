package com.luisfagundes.trip.presentation.viewmodel.effect

import com.luisfagundes.core.presentation.arch.effect.UiEffect

internal sealed class TripDetailsUiEffect : UiEffect {
    data object NavigateBack : TripDetailsUiEffect()
    data class ShowErrorToast(val error: String) : TripDetailsUiEffect()
}
