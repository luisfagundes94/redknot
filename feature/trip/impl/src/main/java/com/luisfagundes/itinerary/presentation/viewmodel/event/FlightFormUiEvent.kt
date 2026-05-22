package com.luisfagundes.itinerary.presentation.viewmodel.event

import com.luisfagundes.core.common.presentation.arch.event.UiEvent
import java.time.LocalDate
import java.time.LocalTime

internal sealed interface FlightFormUiEvent : UiEvent {
    data class InitForm(val tripId: Int, val itemId: String?) : FlightFormUiEvent
    data class UpdateFlightNumber(val value: String) : FlightFormUiEvent
    data class UpdateOrigin(val city: String) : FlightFormUiEvent
    data class UpdateDestination(val city: String) : FlightFormUiEvent
    data class UpdateDuration(val hoursStr: String, val minutesStr: String) : FlightFormUiEvent
    data class UpdateSeatNumber(val value: String) : FlightFormUiEvent
    data class UpdateDate(val date: LocalDate?) : FlightFormUiEvent
    data class UpdateTime(val time: LocalTime) : FlightFormUiEvent
    data class UpdateCompanyName(val name: String) : FlightFormUiEvent
    data class Submit(val tripId: Int) : FlightFormUiEvent
    data object DeleteFlight : FlightFormUiEvent
    data object NavigateBack : FlightFormUiEvent
}
