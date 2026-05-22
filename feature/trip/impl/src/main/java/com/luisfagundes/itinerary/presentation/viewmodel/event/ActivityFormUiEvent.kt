package com.luisfagundes.itinerary.presentation.viewmodel.event

import com.luisfagundes.core.common.presentation.arch.event.UiEvent
import java.time.LocalDate
import java.time.LocalTime

internal sealed interface ActivityFormUiEvent : UiEvent {
    data class InitForm(val tripId: Int, val itemId: String?) : ActivityFormUiEvent
    data class UpdateTitle(val title: String) : ActivityFormUiEvent
    data class UpdateDescription(val description: String) : ActivityFormUiEvent
    data class UpdateLocation(val location: String) : ActivityFormUiEvent
    data class UpdateDate(val date: LocalDate?) : ActivityFormUiEvent
    data class UpdateTime(val time: LocalTime) : ActivityFormUiEvent
    data object NavigateBack : ActivityFormUiEvent
    data class Submit(val tripId: Int) : ActivityFormUiEvent
    data object DeleteActivity : ActivityFormUiEvent
}
