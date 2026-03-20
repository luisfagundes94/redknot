package com.luisfagundes.trip.presentation.viewmodel.action

internal sealed class TripFormUiAction {
    data object NavigateBack : TripFormUiAction()
    data class ShowErrorToast(val error: String) : TripFormUiAction()
}