package com.luisfagundes.trip.presentation.viewmodel.state

import com.luisfagundes.common.domain.model.FieldValidationError
import com.luisfagundes.core.common.presentation.arch.state.UiState
import java.time.LocalDate

internal data class TripFormUiState(
    val title: String = "",
    val titleError: FieldValidationError? = null,
    val startDate: LocalDate? = null,
    val startDateError: FieldValidationError? = null,
    val endDate: LocalDate? = null,
    val endDateError: FieldValidationError? = null,
    val destination: String = "",
    val destinationError: FieldValidationError? = null,
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