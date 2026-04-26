package com.luisfagundes.trip.presentation.viewmodel.effect

import com.luisfagundes.core.common.presentation.arch.effect.UiEffect

internal sealed class TripFormUiEffect : UiEffect {
    data object NavigateBack : TripFormUiEffect()
    data class ShowErrorToast(val error: String) : TripFormUiEffect()
}