package com.luisfagundes.trip.presentation.viewmodel.action

internal sealed class TripFormUiEffect {
    data object NavigateBack : TripFormUiEffect()
    data class ShowErrorToast(val error: String) : TripFormUiEffect()
}