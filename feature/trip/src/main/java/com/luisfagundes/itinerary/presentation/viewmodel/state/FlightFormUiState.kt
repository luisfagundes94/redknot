package com.luisfagundes.itinerary.presentation.viewmodel.state

import com.luisfagundes.core.presentation.arch.state.UiState
import com.luisfagundes.itinerary.domain.model.ItineraryValidationError
import java.time.LocalDate
import java.time.LocalTime

internal data class FlightFormUiState(
    val flightNumber: String = "",
    val flightNumberError: ItineraryValidationError? = null,
    val companyName: String = "",
    val originAirportCity: String = "",
    val destinationName: String = "",
    val destinationAirportCity: String = "",
    val durationHours: String = "0",
    val durationMinutes: String = "0",
    val durationError: ItineraryValidationError? = null,
    val seatNumber: String = "",
    val date: LocalDate? = null,
    val dateError: ItineraryValidationError? = null,
    val time: LocalTime? = null,
    val timeError: ItineraryValidationError? = null,
    val isLoading: Boolean = false
) : UiState {
    val isFormValid: Boolean
        get() {
            val hasNoErrors = listOf(
                flightNumberError,
                durationError,
                dateError,
                timeError
            ).all { it == null }
            val hasAllRequiredFields = flightNumber.isNotBlank() &&
                    originAirportCity.isNotBlank() &&
                    destinationName.isNotBlank() && destinationAirportCity.isNotBlank() &&
                    date != null && time != null
            return hasNoErrors && hasAllRequiredFields
        }
}
