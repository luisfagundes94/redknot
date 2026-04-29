package com.luisfagundes.itinerary.presentation.viewmodel.state

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.core.common.presentation.arch.state.UiState
import java.time.LocalDate
import java.time.LocalTime

internal data class FlightFormUiState(
    val flightNumber: String = "",
    val flightNumberError: FieldValidationError? = null,
    val companyName: String = "",
    val originAirportCity: String = "",
    val destinationAirportCity: String = "",
    val durationHours: String = "0",
    val durationMinutes: String = "0",
    val durationError: FieldValidationError? = null,
    val seatNumber: String = "",
    val date: LocalDate? = null,
    val dateError: FieldValidationError? = null,
    val time: LocalTime? = null,
    val timeError: FieldValidationError? = null,
    val isLoading: Boolean = false
) : UiState {
    private val hasAllRequiredFields: Boolean
        get() = flightNumber.isNotBlank() &&
                originAirportCity.isNotBlank() &&
                destinationAirportCity.isNotBlank() &&
                date != null &&
                time != null

    private val hasNoErrors: Boolean
        get() = flightNumberError == null &&
                durationError == null &&
                dateError == null &&
                timeError == null

    val isFormValid: Boolean
        get() = hasAllRequiredFields && hasNoErrors
}
