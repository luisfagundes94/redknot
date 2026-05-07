package com.luisfagundes.trip.presentation.viewmodel.effect

import com.luisfagundes.core.common.presentation.arch.effect.UiEffect

internal sealed interface TripDetailsUiEffect : UiEffect {
    data object NavigateBack : TripDetailsUiEffect
    data class ShowErrorToast(val error: String) : TripDetailsUiEffect
}
