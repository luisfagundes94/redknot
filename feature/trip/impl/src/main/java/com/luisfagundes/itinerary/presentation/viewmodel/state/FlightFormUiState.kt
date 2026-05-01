package com.luisfagundes.itinerary.presentation.viewmodel.state

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.core.common.presentation.arch.state.UiState
import java.time.LocalDate
import java.time.LocalTime

internal sealed interface FlightFormUiState : UiState {
    data object Loading : FlightFormUiState

    data class Content(
        val editingItemId: String? = null,
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
        val isLoading: Boolean = false,
        val tripStartDate: LocalDate? = null,
    ) : FlightFormUiState {
        val isEditMode: Boolean get() = editingItemId != null

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

        val isFormValid: Boolean get() = hasAllRequiredFields && hasNoErrors
    }
}
