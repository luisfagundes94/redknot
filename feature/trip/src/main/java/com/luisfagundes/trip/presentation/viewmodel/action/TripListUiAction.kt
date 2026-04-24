package com.luisfagundes.trip.presentation.viewmodel.action

import com.luisfagundes.core.presentation.arch.action.UiAction

sealed interface TripListUiAction : UiAction {
    data object NavigateToTripForm : TripListUiAction
    data class NavigateToTripDetails(val id: Int) : TripListUiAction
}