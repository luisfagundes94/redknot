package com.luisfagundes.itinerary.presentation.viewmodel.effect

import com.luisfagundes.core.presentation.arch.effect.UiEffect

internal sealed class ActivityFormUiEffect : UiEffect {
    data object NavigateBack : ActivityFormUiEffect()
    data class ShowErrorToast(val error: String) : ActivityFormUiEffect()
}
