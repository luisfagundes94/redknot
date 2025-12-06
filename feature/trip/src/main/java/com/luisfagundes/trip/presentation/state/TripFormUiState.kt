package com.luisfagundes.trip.presentation.state

import com.luisfagundes.trip.domain.model.ValidationError
import java.time.LocalDate

internal data class TripFormUiState(
    val title: String = "",
    val titleError: ValidationError? = null,
    val startDate: LocalDate? = null,
    val startDateError: ValidationError? = null,
    val endDate: LocalDate? = null,
    val endDateError: ValidationError? = null,
    val destination: String = "",
    val destinationError: ValidationError? = null,
) {
    val isFormValid: Boolean
        get() {
            val hasNoErrors = listOf(
                titleError,
                startDateError,
                endDateError,
                destinationError
            ).all { it == null }

            val hasAllRequiredFields = title.isNotBlank() &&
                    startDate != null &&
                    endDate != null &&
                    destination.isNotBlank()

            return hasNoErrors && hasAllRequiredFields
        }
}