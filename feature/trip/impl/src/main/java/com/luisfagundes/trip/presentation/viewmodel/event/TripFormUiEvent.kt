package com.luisfagundes.trip.presentation.viewmodel.event

import com.luisfagundes.core.common.presentation.arch.event.UiEvent
import java.time.LocalDate

internal sealed interface TripFormUiEvent : UiEvent {
    data class UpdateTitle(val title: String) : TripFormUiEvent
    data class UpdateStartDate(val startDate: LocalDate?) : TripFormUiEvent
    data class UpdateEndDate(val endDate: LocalDate?) : TripFormUiEvent
    data class UpdateDestination(val destination: String) : TripFormUiEvent
    data object Submit : TripFormUiEvent
}
