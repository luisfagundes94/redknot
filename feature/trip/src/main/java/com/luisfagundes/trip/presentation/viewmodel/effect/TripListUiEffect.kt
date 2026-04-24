package com.luisfagundes.trip.presentation.viewmodel.effect

import com.luisfagundes.core.presentation.arch.effect.UiEffect

sealed interface TripListUiEffect : UiEffect {
    data object NavigateToTripForm : TripListUiEffect
    data class NavigateToTripDetails(val id: Int) : TripListUiEffect
}