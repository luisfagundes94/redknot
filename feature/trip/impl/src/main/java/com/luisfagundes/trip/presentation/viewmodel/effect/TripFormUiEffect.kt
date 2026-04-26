package com.luisfagundes.trip.presentation.viewmodel.effect

internal sealed class TripFormUiEffect {
    data object NavigateBack : TripFormUiEffect()
    data class ShowErrorToast(val error: String) : TripFormUiEffect()
}