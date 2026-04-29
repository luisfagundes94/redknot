package com.luisfagundes.trip.presentation.viewmodel.state

import com.luisfagundes.common.domain.model.DateValidationError
import com.luisfagundes.core.common.presentation.arch.state.UiState
import com.luisfagundes.common.domain.model.ValidationError
import java.time.LocalDate

internal data class TripFormUiState(
    val title: String = "",
    val titleError: ValidationError? = null,
    val startDate: LocalDate? = null,
    val startDateError: DateValidationError? = null,
    val endDate: LocalDate? = null,
    val endDateError: DateValidationError? = null,
    val destination: String = "",
    val destinationError: ValidationError? = null,
    val isLoading: Boolean = false
) : UiState {
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